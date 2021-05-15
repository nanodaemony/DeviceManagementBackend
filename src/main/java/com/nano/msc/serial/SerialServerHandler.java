package com.nano.msc.serial;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.common.utils.SpringContextUtil;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.devicedata.context.DeviceDataContext;
import com.nano.msc.devicedata.context.DeviceDataHandler;
import com.nano.msc.websocket.RealTimeDeviceDataServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;
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
public class SerialServerHandler extends ChannelInboundHandlerAdapter {

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
        // 导入必要的类!!!!!!!!!
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        medicalDeviceRepository = context.getBean(InfoMedicalDeviceRepository.class);
        deviceDataCollectionRepository = context.getBean(InfoDeviceDataCollectionRepository.class);
        deviceDataContext = context.getBean(DeviceDataContext.class);
        dataHandlerMap = deviceDataContext.getDataHandlerMap();
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info(ctx.channel().remoteAddress().toString() + ": {}", msg.toString());
        // 接收的数据
        String data = msg.toString();

        // 说明是心跳报文
        if (data.startsWith(SerialStaticInfo.HEART_MESSAGE_PREFIX)) {
            handleCollectorHeartMessage(ctx, data);

            // 说明是仪器数据报文
        } else if (data.startsWith(SerialStaticInfo.DEVICE_DATA_MESSAGE_PREFIX)) {
            handleDeviceDataMessage(ctx, data);
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
        // 获取采集器唯一ID号
        String uniqueId = values[2];

        // 说明当前Map中不含这个采集器
        if (!SerialStaticInfo.collectionMap.containsKey(uniqueId)) {
            // 则放入当前的Map中
            SerialStaticInfo.collectionMap.put(uniqueId, new SerialDeviceEntity());
        }
        // 说明Map中包含这个采集器,获取对应的采集信息实体
        SerialDeviceEntity entity = SerialStaticInfo.collectionMap.get(uniqueId);
        // 说明没有这个采集器对应的仪器信息
        if (entity.getMedicalDevice() == null) {
            // 根据采集器ID查询其对应的医疗仪器
            InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByCollectorUniqueId(uniqueId);
            // 说明当前采集器还没有对应医疗仪器
            if (medicalDevice == null) {
                log.error("当前采集器没有对应的仪器信息: " + uniqueId);
            } else {
                // 记录当前的仪器信息
                entity.setMedicalDevice(medicalDevice);
            }
        }
        // 更新该采集器心跳报文接收时间,采集信息不为空才更新
        if (entity.getDeviceDataCollection() != null) {
            // 更新采集的心跳时间戳为当前时间
            entity.getDeviceDataCollection().setSerialCollectorLastHeartMessageTime(System.currentTimeMillis());
        }
        // 返回收到心跳包
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
            log.info("接收串口仪器数据格式不准确: " + data);
            return;
        }
        // 获取包含的信息
        String uniqueId = values[2];
        String deviceData = values[3];

        // 连这个采集器的信息都不包含,直接返回不予处理
        if (!SerialStaticInfo.collectionMap.containsKey(uniqueId)) {
            log.error("不包含当前采集器信息,仪器数据为:" + data);
            return;
        }

        // 获取Entity
        SerialDeviceEntity entity = SerialStaticInfo.collectionMap.get(uniqueId);
        // 接着判断是否有对应的仪器信息,如果没有则直接返回
        if (entity.getMedicalDevice() == null) {
            log.error("不包含当前采集的仪器信息,仪器数据为:" + data);
            return;
        }
        // 获取对应的仪器信息
        InfoMedicalDevice device = entity.getMedicalDevice();
        InfoDeviceDataCollection collection = entity.getDeviceDataCollection();

        if (collection == null) {
            // 看看数据库中是否有没有结束的信息,如果有则找到
            List<InfoDeviceDataCollection> collectionList = deviceDataCollectionRepository.findByCollectorUniqueIdAndCollectionStatus(uniqueId, CollectionStatusEnum.COLLECTING.getCode());
            // 如果有,就获取最后一条采集信息即可
            if (collectionList != null && collectionList.size() != 0) {
                collection = collectionList.get(collectionList.size() - 1);
            }
        }
        // 接着判断是否是新的采集场次的开始
        if (collection == null || collection.getCollectionStatus().equals(CollectionStatusEnum.FINISHED.getCode())) {
            // 此时新生成采集场次
            collection = new InfoDeviceDataCollection();
            BeanUtil.copyProperties(device, collection);
            // 设置采集状态为正在采集
            collection.setMedicalDeviceId(device.getId());
            collection.setCollectionStatus(CollectionStatusEnum.COLLECTING.getCode());
            collection.setCollectorUniqueId(uniqueId);
            // 设置开始时间为现在
            collection.setCollectionStartTime(TimestampUtils.getCurrentTimeForDataBase());
            // 保存DataCollection信息到数据库,获取到分配的CollectionNumber
            collection = deviceDataCollectionRepository.save(collection);
            // 加入到缓存中
            entity.setDeviceDataCollection(collection);
        }
        // 构造发送到解析器的数据
        String deviceDataRaw = collection.getCollectionNumber() + DATA_SEPARATOR
                + device.getSerialNumber() + DATA_SEPARATOR
                + deviceData;

        // 到这里,说明采集信息也有了,直接处理数据
        // 获取数据处理器
        Object result = dataHandlerMap.get(collection.getDeviceCode()).getDataParser().parseDeviceData(deviceDataRaw);
        if (result == null) {
            log.error("串口仪器数据解析与存储失败:" + data.toString());
            return;
        } else {
            log.info("解析到串口仪器数据: " + result.toString());
        }
        // 仪器数据实时推送到前端
        RealTimeDeviceDataServer.sendDeviceRealTimeDataToClient(collection.getCollectionNumber(), collection.getDeviceCode(), JSON.toJSONString(result));

        // 更新上次接收仪器数据报文时间
        collection.setSerialCollectorLastDeviceDataMessageTime(System.currentTimeMillis());

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

