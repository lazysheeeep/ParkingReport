package com.potato.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Car {

  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;

  private String username;

  private String plateColor;

  private String plateNumber;

  private String brand;

  private String type;
}
