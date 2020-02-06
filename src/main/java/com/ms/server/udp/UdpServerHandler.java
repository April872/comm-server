package com.ms.server.udp;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ms.server.entity.BaseDataPointInfo;
import com.ms.server.entity.DataList;
import com.ms.server.init.InitDBData;
import com.ms.server.mapper.DataListMapper;
import com.ms.server.thread.DataHandle;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;



public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger log = LoggerFactory.getLogger(UdpServerHandler.class);

    //用来计算server接收到多少UDP消息
    private static long count = 0;

    @Autowired
    private InitDBData initDBData;
    @Autowired
    private DataListMapper dataListMapper;
/*
    @Autowired
    private DataHandle dataHandle;
*/
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

        String receiveMsg = packet.content().toString(CharsetUtil.UTF_8);
        count++;
        log.info("Received UDP Msg:" + String.valueOf(count) + " " + receiveMsg);

 //       log.info(InitDBData.baseDataPointInfoMap.get(1).toString());

         //判断接受到的UDP消息是否正确
        if (StringUtils.isNotBlank(receiveMsg)) {

            //DataHandle dataHandle = new DataHandle();
            //dataHandle.InsertDataList(receiveMsg);
            DataHandle.getDataHandle().InsertDataList(receiveMsg);

 /*       for (Map.Entry<Long, BaseDataPointInfo> entry : StartupEvent.baseDataPointInfoMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        };
*/
      }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }

    public Timestamp getTime() {
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

}