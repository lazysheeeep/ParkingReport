package com.potato.common;

import java.util.HashMap;

public class ErrorCodeMap {
  private HashMap<String,String> errorMap = new HashMap<String,String>();

  // 1xxx 用户模块错误
  public ErrorCodeMap() {
    errorMap.put("1001","两次密码输入不一致！");
    errorMap.put("1002","验证码错误");
    errorMap.put("1003","用户名或密码错误");
    errorMap.put("1004","Token格式验证失败");
    errorMap.put("1005","Token已过期");

    errorMap.put("2001","车牌号不能为空");
    errorMap.put("2002","地点不能为空");
  }

  public String getErrorMessage(String code) {
    return errorMap.get(code);
  }
}
