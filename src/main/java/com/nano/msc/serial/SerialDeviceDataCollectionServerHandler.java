package com.nano.msc.serial;

import com.alibaba.fastjson.JSON;
import com.nano.msc.GlobalContext;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.common.utils.SpringContextUtil;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.devicedata.context.DeviceDataContext;
import com.nano.msc.devicedata.context.DeviceDataHandler;
import com.nano.msc.websocket.RealTimeDeviceDataServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.Resource;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端处理器,用于采集串口类仪器的数据
 * @author nano
 *
 * 通信协议:
 * 1. 采集器:
 *      心跳包: #1#UniqueId#, 服务器根据UniqueId刷新采集器时间;
 *      连接请求包: #2#UniqueId#serialNumber#CollectionNumber
 *      仪器数据包: #3#UniqueId#serialNumber#collectionNumber#deviceData
 *
 * 2. 服务端:
 *      定时任务1: 检查采集列表中的采集任务是否在十分钟内收到了采集器的心跳信息,如果没有则认定当次采集结束;
 *      定时任务2: 检查采集列表中的采集任务是否在十分钟内收到了仪器数据信息,如果没有则认定当次采集结束;
 *
 *
 * 假设: 能够完全正常采集,也就是
 **/
@Slf4j
@Component
public class SerialDeviceDataCollectionServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 数据间隔
     */
    private static final String DATA_SEPARATOR = "#";

    @Autowired
    @Resource
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository deviceDataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationService usageEvaluationService;

    @Autowired
    private DeviceDataContext deviceDataContext;

    /**
     * 数据处理器的Map
     */
    private Map<Integer, DeviceDataHandler> dataHandlerMap;

    /**
     * 客户端连接成功时会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("采集器已连接: " + ctx.channel().remoteAddress().toString());
        // 这里注入必要的类对象!!!!!!!!!
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        medicalDeviceRepository = context.getBean(InfoMedicalDeviceRepository.class);
        deviceDataCollectionRepository = context.getBean(InfoDeviceDataCollectionRepository.class);
        deviceDataContext = context.getBean(DeviceDataContext.class);
        usageEvaluationService = context.getBean(InfoDeviceUsageEvaluationService.class);
        dataHandlerMap = deviceDataContext.getDataHandlerMap();
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收的数据
        String data = msg.toString();
        // 说明是心跳报文
        if (data.startsWith(MessagePrefix.HEART_MESSAGE_PREFIX)) {
            log.info("采集器心跳:" + ctx.channel().remoteAddress().toString() + ": {}", data);
            handleCollectorHeartMessage(ctx, data);
            // 说明是仪器数据报文
        } else if (data.startsWith(MessagePrefix.DEVICE_DATA_MESSAGE_PREFIX)) {
            log.info("采集器数据:" + ctx.channel().remoteAddress().toString() + ": {}", data);
            handleDeviceDataMessage(ctx, data);
        } else {
            log.info("串口接收未知数据:" + ctx.channel().remoteAddress().toString() + ": {}", data);
        }
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 处理采集器心跳消息
     */
    private void handleCollectorHeartMessage(ChannelHandlerContext ctx, String data) {
        // #1#UniqueId#
        String[] values = data.split(DATA_SEPARATOR);
        if (values.length < 3) {
            log.info("心跳报文格式错误:" + data);
            return;
        }
        // 获取采集器唯一ID号,如: DC-75-0001
        String uniqueId = values[2];
        if (!GlobalContext.dataCollectorSet.contains(uniqueId)) {
            log.info("该采集器并未进行系统录入.");
            return;
        }
        // 查询采集信息
        InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionStatusAndCollectorUniqueId(CollectionStatusEnum.COLLECTING.getCode(), uniqueId);
        // 如果不为空则记录当前接收时间
        if (collection != null) {
            // 更新接收心跳的时间
            collection.setLastReceiveHeartMessageTime(TimestampUtils.getCurrentTimeForDataBase());
            deviceDataCollectionRepository.save(collection);
        }
        // 如果是合法注册的采集器则直接返回收到心跳包
        ctx.write("#1#" + Long.parseLong(("" + System.currentTimeMillis() / 1000).substring(5)) + "#");
        ctx.flush();
    }


    /**
     * 处理采集器发送仪器数据消息
     */
    private void handleDeviceDataMessage(ChannelHandlerContext ctx, String data) {

        // 仪器数据格式: #3#DC-75-0001#1231231231237829304902424348293048902
        String[] values = data.split(DATA_SEPARATOR);
        if (values.length < 4) {
            log.error("接收串口仪器数据格式不准确: " + data);
            return;
        }
        // 获取包含的信息
        String uniqueId = values[2];
        String deviceData = values[3];

        // 连这个采集器的信息都不包含,直接返回不予处理
        if (!GlobalContext.dataCollectorSet.contains(uniqueId)) {
            log.error("不包含当前采集器信息,仪器数据为:" + data);
            return;
        }
        // 寻找找到数据库中的采集信息
        InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionStatusAndCollectorUniqueId(CollectionStatusEnum.COLLECTING.getCode(), uniqueId);
        // 说明没有采集信息,此时直接生成一个新的采集信息
        if (collection == null) {
            collection = new InfoDeviceDataCollection();
            // 通过采集器的序号找到其对应的仪器信息
            InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByCollectorUniqueId(uniqueId);
            collection.setMedicalDeviceId(medicalDevice.getId());
            collection.setDeviceCode(medicalDevice.getDeviceCode());
            collection.setCollectorUniqueId(uniqueId);
            collection.setCollectionStatus(CollectionStatusEnum.COLLECTING.getCode());
            collection.setSerialNumber(medicalDevice.getSerialNumber());

            collection.setCollectionStartTime(TimestampUtils.getCurrentTimeForDataBase());
            collection.setCollectionFinishTime(TimestampUtils.getCurrentTimeForDataBase());
            collection.setLastReceiveDeviceDataTime(TimestampUtils.getCurrentTimeForDataBase());
            collection.setLastReceiveHeartMessageTime(TimestampUtils.getCurrentTimeForDataBase());
            // 存入数据库
            collection = deviceDataCollectionRepository.save(collection);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 添加本次采集的默认使用评价信息
            usageEvaluationService.addDefaultDeviceUsageEvaluationInfo(collection.getCollectionNumber(), collection.getDeviceCode(), medicalDevice.getSerialNumber(), medicalDevice.getDeviceDepartment());
        }
        // 构造发送到解析器的数据
        String deviceDataRaw = collection.getCollectionNumber() + DATA_SEPARATOR
                + collection.getSerialNumber() + DATA_SEPARATOR
                + deviceData;
        // 进行数据解析
        Object result = dataHandlerMap.get(collection.getDeviceCode()).getDataManager().parseDeviceData(deviceDataRaw);
        if (result == null) {
            log.error("串口仪器数据解析与存储失败:" + data);
            return;
        } else {
            log.info("获取并解析到串口仪器数据: " + JSON.toJSONString(result));
        }
        // 仪器数据实时推送到前端
        RealTimeDeviceDataServer.sendDeviceRealTimeDataToClient(collection.getCollectionNumber(), collection.getDeviceCode(), JSON.toJSONString(result));
        // 更新上次接收仪器数据报文时间
        collection.setLastReceiveDeviceDataTime(TimestampUtils.getCurrentTimeForDataBase());
        deviceDataCollectionRepository.save(collection);
        // 返回这个表明正常收到仪器数据
        ctx.write("#3#");
        ctx.flush();
    }

    public static void main(String[] args) {
        String[] values = "#3#DC-75-0001#1231231231237829304902424348293048902".split("#");
        System.out.println(values[2]);
        System.out.println(values[3]);
    }

}

