package com.ms.server.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class UdpServer {

    private static final Logger log= LoggerFactory.getLogger(UdpServer.class);

//    private static final int PORT = Integer.parseInt(System.getProperty("port", "7686"));
    //异步调用,使用指定的线程池
    @Async("TaskExecutor")
    public void run(int udpReceivePort) {

        EventLoopGroup group = new NioEventLoopGroup();
        log.info("--------------------------------------------------------------------" );
        log.info(" UDP Server start!!!  UDP Receive msg Port:" + udpReceivePort );

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            b.bind(udpReceivePort).sync().channel().closeFuture().sync();//.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
