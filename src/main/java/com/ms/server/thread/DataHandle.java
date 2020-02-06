package com.ms.server.thread;

import com.ms.server.comm.RedisUtil;
import com.ms.server.entity.BaseDataPointInfo;
import com.ms.server.entity.DataList;
import com.ms.server.init.InitDBData;
import com.ms.server.mapper.DataListMapper;
import com.ms.server.udp.UdpServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
@Async
public class DataHandle {
    private static final Logger log = LoggerFactory.getLogger(DataHandle.class);

    private  static DataHandle dataHandle;
    @Autowired
    private InitDBData initDBData;

    @Autowired
    private DataListMapper dataListMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public void InsertDataList(String receiveMsg) {

        //数据格式: string: dadaid|time,value
        Integer result = 0;

        String[] strarray = receiveMsg.split("\\|");
        Long dataid = Long.parseLong(strarray[0]);
        if (dataid > 0) {
            //判断数据点是否存在
            BaseDataPointInfo baseDataPointInfo = initDBData.baseDataPointInfoMap.get(dataid);
            if (baseDataPointInfo == null) //判断该数据点的基本信息是否为空
            {
                log.info("上报数据点ID：" + dataid + " 的基本信息数据为空！");
                return;
            }
            DataList dataList = new DataList();
            dataList.setDataId(baseDataPointInfo.getDataId());
            dataList.setValue(Double.parseDouble(strarray[1]));
            dataList.setDataTypeId(baseDataPointInfo.getDataTypeId());
            dataList.setDeviceId(baseDataPointInfo.getDeviceId());
            dataList.setCollectionTime(new Timestamp(System.currentTimeMillis()));
            result = dataListMapper.insert(dataList);
            if (result > 0) {
                log.info("数据插入数据库成功！" + dataList);

            }
            String strtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dataList.getCollectionTime());
            String strkey = Long.toString(baseDataPointInfo.getDataId()) +"|" + strtime;
            String strvalue = strarray[1];//String.valueOf(dataList.getValue());
            if (redisUtil.set(strkey, strvalue)){
                log.info("数据写入REDIS成功！"  + strkey + "," + strvalue );

            }
            redisTemplate.convertAndSend("channel1", strkey + "," +strvalue);

        }


    }

    @PostConstruct
    private  void  init(){
        dataHandle = this;

    }

    //静态get方法
    public static DataHandle getDataHandle() {
        return dataHandle;
    }
}
