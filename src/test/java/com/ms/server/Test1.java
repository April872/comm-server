package com.ms.server;

import com.ms.server.entity.BaseDataPointInfo;
import com.ms.server.entity.DataList;
import com.ms.server.init.InitDBData;
import com.ms.server.mapper.BaseDataPointInfoMapper;

import com.ms.server.mapper.DataListMapper;
import com.ms.server.udp.UdpServerHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
    private static final Logger log = LoggerFactory.getLogger(UdpServerHandler.class);


    @Autowired
    private BaseDataPointInfoMapper basedataPointInfoMapper;
    @Autowired
    private  InitDBData initDBData;

    @Resource
    private DataListMapper dataListMapper;
    @Test
    public void TestDataPointList() {


        Integer result = 0;
      //  List<BaseDataPointInfo> datapointinfolist = basedataPointInfoMapper.selectList(null);
        //datapointinfolist.forEach(System.out::println);

        //Map<Long, BaseDataPointInfo> baseDataPointInfoMap = datapointinfolist.stream().collect(Collectors.toMap(BaseDataPointInfo::getDataId, Function.identity()));
        //Map<Long, BaseDataPointInfo> baseDataPointInfoMap1 = initDBData.baseDataPointInfoMap;
        BaseDataPointInfo baseDataPointInfo = initDBData.baseDataPointInfoMap.get((long)1);
        DataList dataList = new DataList();
        dataList.setDataId(baseDataPointInfo.getDataId());
        dataList.setValue(12.44);
        dataList.setDataTypeId(baseDataPointInfo.getDataTypeId());
        dataList.setDeviceId(baseDataPointInfo.getDeviceId());
        result = dataListMapper.insert(dataList);
        if (result > 0) {
            log.info("插入数据成功！---" + dataList);

        }

  /*      for (Map.Entry<Long,BaseDataPointInfo> entry : baseDataPointInfoMap1.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        };*/


    }

}
/*


public class SerialPortService {
    public static SerialPort mSerialport = null;
    //	private SimpMessagingTemplate simpMessage;
    private DataAcquisitionService das;
    private SystemService systemService;
    private SysParamMapper sysParamMapper;

    public SerialPortService() {
        this.das = SpringContextUtil.getBean(DataAcquisitionService.class);
        this.systemService = SpringContextUtil.getBean(SystemService.class);
        this.sysParamMapper = SpringContextUtil.getBean(SysParamMapper.class);
    }

}*/
