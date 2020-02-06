package com.ms.server.init;


import com.ms.server.entity.BaseDataPointInfo;
import com.ms.server.mapper.BaseDataPointInfoMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class InitDBData {

    @Autowired
    private  BaseDataPointInfoMapper basedataPointInfoMapper;

    private  List<BaseDataPointInfo>  datapointinfolist;

    public  static Map<Long, BaseDataPointInfo> baseDataPointInfoMap;

    @PostConstruct
    public  void init(){
        //获取所有数据点的基本信息
        datapointinfolist = basedataPointInfoMapper.selectList(null);
        baseDataPointInfoMap = datapointinfolist.stream().collect(Collectors.toMap(BaseDataPointInfo::getDataId, Function.identity()));
        for (Map.Entry<Long,BaseDataPointInfo> entry : baseDataPointInfoMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        };

    }
    @PreDestroy
    public void destroy() {
        //系统运行结束
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void RefashData() {
        //每1小时执行一次刷新
        init();
    }


}
