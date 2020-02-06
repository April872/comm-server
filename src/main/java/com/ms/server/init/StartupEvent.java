package com.ms.server.init;

import com.ms.server.mapper.BaseDataPointInfoMapper;
import com.ms.server.comm.SysConfig;
import com.ms.server.udp.UdpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class StartupEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(StartupEvent.class);

    private static ApplicationContext context;


    @Autowired
    private BaseDataPointInfoMapper basedataPointInfoMapper;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {

            context = contextRefreshedEvent.getApplicationContext();


            SysConfig sysConfig = (SysConfig) context.getBean(SysConfig.class);

            MyProperties myProperties = (MyProperties) context.getBean(MyProperties.class);
            if (myProperties.getUdp()) {

                //接收UDP消息并保存至redis中
                UdpServer udpServer = (UdpServer) StartupEvent.getBean(UdpServer.class);
                udpServer.run(sysConfig.getUdpReceivePort());


            }


            //初始化数据


//            这里可以开启多个线程去执行不同的任务


        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public static Object getBean(Class beanName) {
        return context != null ? context.getBean(beanName) : null;
    }
}
