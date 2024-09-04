package com.potato.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

  //用户id随机
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  //用户名
  private String username;

  //密码
  private String password;

  //用户手机号
  private String phone;

  //用户性别
  private Character sex;

  //用户年龄
  private Short age;

  //用户头像
  private String avatar;

  //用户角色 普通用户 1|审核员 2|管理员 3
  private String roleId;

  //用户状态 1 正常使用|0 禁止使用
  private String status;
}
