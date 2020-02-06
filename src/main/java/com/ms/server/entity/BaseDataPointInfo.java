package com.ms.server.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BaseDataPointInfo {

  private long dataId;
  private String dataName;
  private String location;
  private String mark;
  private long dataTypeId;
  private long deviceId;
  private String minValue;
  private String maxValue;
  private String createBy;
  private java.sql.Timestamp createTime;
  private String updateBy;
  private java.sql.Timestamp updateTime;


}
