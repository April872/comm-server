package com.ms.server.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class DataList  {

  @TableId(value = "id", type = IdType.AUTO)
  private long id;
  private long dataId;
  private long deviceId;
  private long dataTypeId;
  private double value;
  private java.sql.Timestamp collectionTime;
  private long status;

}
