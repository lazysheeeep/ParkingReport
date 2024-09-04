package com.potato.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RList<T> {

  // 状态码 1 成功|0 失败
  private Integer code;

  // 信息
  private String message;


  private int total;

  // 数据
  private T data;


  public static <T> RList<T> success(T object,int total) {
    RList<T> r = new RList<T>();
    r.data = object;
    r.total = total;
    r.code = 1;
    return r;
  }

  public static <T> RList<T> error(String message) {
    RList<T> r = new RList<T>();
    r.message = message;
    r.code = 0;
    return r;
  }
}
