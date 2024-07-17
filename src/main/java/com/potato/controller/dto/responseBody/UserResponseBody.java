package com.potato.controller.dto.responseBody;

import lombok.Data;

@Data
public class UserResponseBody {

  //用户id
  private Long userId;

  //用户名称
  private String userName;

  //手机号
  private String phone;

  //用户性别
  private Character sex;

  //用户年龄
  private Short age;

  //用户头像
  private String avatar;

  //用户角色 普通用户 1|审核员 2|管理员 3
  private String roleId;
}
