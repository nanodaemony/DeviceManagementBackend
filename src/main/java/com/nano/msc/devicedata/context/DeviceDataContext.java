package com.nano.msc.devicedata.context;

import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.parser.DataParserAiQin600A;
import com.nano.msc.devicedata.parser.DataParserAiQin600B;
import com.nano.msc.devicedata.parser.DataParserAiQin600C;
import com.nano.msc.devicedata.parser.DataParserBaoLaiTeA8;
import com.nano.msc.devicedata.parser.DataParserLiBangEliteV8;
import com.nano.msc.devicedata.parser.DataParserMaiRuiT8;
import com.nano.msc.devicedata.parser.DataParserMaiRuiWatoex65;
import com.nano.msc.devicedata.parser.DataParserMeiDunLiVista;
import com.nano.msc.devicedata.parser.DataParserNuoHe9002S;
import com.nano.msc.devicedata.parser.DataParserPuKeYY106;
import com.nano.msc.devicedata.parser.DataParserYiAn800A;
import com.nano.msc.devicedata.parser.DeviceDataParser;
import com.nano.msc.devicedata.repository.AiQin600ARepository;
import com.nano.msc.devicedata.repository.AiQin600BRepository;
import com.nano.msc.devicedata.repository.AiQin600CRepository;
import com.nano.msc.devicedata.repository.BaoLaiTeA8Repository;
import com.nano.msc.devicedata.repository.LiBangEliteV8Repository;
import com.nano.msc.devicedata.repository.MaiRuiT8Repository;
import com.nano.msc.devicedata.repository.MaiRuiWatoex65Repository;
import com.nano.msc.devicedata.repository.MeiDunLiVistaRepository;
import com.nano.msc.devicedata.repository.NuoHe9002sRepository;
import com.nano.msc.devicedata.repository.PuKeYy106Repository;
import com.nano.msc.devicedata.repository.YiAn8700ARepository;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 23:39
 */
@Component
public class DeviceDataContext implements InitializingBean, ApplicationContextAware {

    private ApplicationContext context;

    @Getter
    private Map<Integer, DeviceDataHandler> dataHandlerMap;


    /**
     * 容器生成后进行初始化扫描注册
     */
    @Override
    public void afterPropertiesSet() {
        // UDP仪器
        dataHandlerMap.put(MedicalDeviceEnum.NUO_HE_NW9002S.getDeviceCode(), new DeviceDataHandler<>(context.getBean(NuoHe9002sRepository.class), context.getBean(DataParserNuoHe9002S.class)));
        dataHandlerMap.put(MedicalDeviceEnum.PU_KE_YY106.getDeviceCode(), new DeviceDataHandler<>(context.getBean(PuKeYy106Repository.class), context.getBean(DataParserPuKeYY106.class)));
        dataHandlerMap.put(MedicalDeviceEnum.BAO_LAI_TE_A8.getDeviceCode(), new DeviceDataHandler<>(context.getBean(BaoLaiTeA8Repository.class), context.getBean(DataParserBaoLaiTeA8.class)));
        dataHandlerMap.put(MedicalDeviceEnum.YI_AN_8700A.getDeviceCode(), new DeviceDataHandler<>(context.getBean(YiAn8700ARepository.class), context.getBean(DataParserYiAn800A.class)));
        // TCP仪器
        dataHandlerMap.put(MedicalDeviceEnum.MAI_RUI_T8.getDeviceCode(), new DeviceDataHandler<>(context.getBean(MaiRuiT8Repository.class), context.getBean(DataParserMaiRuiT8.class)));
        dataHandlerMap.put(MedicalDeviceEnum.MAI_RUI_WATOEX_65.getDeviceCode(), new DeviceDataHandler<>(context.getBean(MaiRuiWatoex65Repository.class), context.getBean(DataParserMaiRuiWatoex65.class)));
        dataHandlerMap.put(MedicalDeviceEnum.LI_BANG_ELITE_V8.getDeviceCode(), new DeviceDataHandler<>(context.getBean(LiBangEliteV8Repository.class), context.getBean(DataParserLiBangEliteV8.class)));
        // 串口仪器
        dataHandlerMap.put(MedicalDeviceEnum.AI_QIN_EGOS600A.getDeviceCode(), new DeviceDataHandler<>(context.getBean(AiQin600ARepository.class), context.getBean(DataParserAiQin600A.class)));
        dataHandlerMap.put(MedicalDeviceEnum.AI_QIN_EGOS600B.getDeviceCode(), new DeviceDataHandler<>(context.getBean(AiQin600BRepository.class), context.getBean(DataParserAiQin600B.class)));
        dataHandlerMap.put(MedicalDeviceEnum.AI_QIN_EGOS600C.getDeviceCode(), new DeviceDataHandler<>(context.getBean(AiQin600CRepository.class), context.getBean(DataParserAiQin600C.class)));
        dataHandlerMap.put(MedicalDeviceEnum.MEIDUNLI_EEG_VISTA.getDeviceCode(), new DeviceDataHandler(context.getBean(MeiDunLiVistaRepository.class), context.getBean(DataParserMeiDunLiVista.class)));
    }

    /**
     * 注入上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.context == null) {
            this.context = applicationContext;
        }
    }

    public DeviceDataContext() {
        this.dataHandlerMap = new HashMap<>();
    }

}
