package com.ms.server.comm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix="sysfig")
public class SysConfig {
    private int UdpReceivePort;//UDP消息接收端口

    //线程池信息
    private int CorePoolSize;

    private int MaxPoolSize;

    private int KeepAliveSeconds;

    private int QueueCapacity;

}
