package com.ms.server.init;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix="ms-server")
public class MyProperties {
    //通信协议
    private Boolean tcp;

    private Boolean udp;

    private Boolean http;

    private Boolean modbus;

}