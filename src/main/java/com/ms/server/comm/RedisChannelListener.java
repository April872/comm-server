package com.ms.server.comm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;


import java.io.UnsupportedEncodingException;

public class RedisChannelListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(RedisChannelListener.class);

 /*   @Autowired
    private static RedisUtil redisUtil;*/

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Async("TaskExecutor")
     public void onMessage(Message message, byte[] bytes) {

        byte[] channel = message.getChannel();
        byte[] body = message.getBody();

        try {
            String title = new String(channel, "UTF-8");
            String content = new String(body, "UTF-8");
            String  strkey=content.substring(0, content.indexOf(","));
            log.info("收到Redis消息--> 频道:" + title  + "  内容:" + content);
            RedisUtil.getRedisUtil().del(strkey);
            log.info("删除Redis数据：" +  strkey);
            //redisTemplate.delete(strkey);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}