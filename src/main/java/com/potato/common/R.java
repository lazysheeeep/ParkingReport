package com.potato.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

  // 状态码 1 成功|0 失败
  private Integer code;

  // 信息
  private String message;

  // 数据
  private T data;

  // 动态数据
  private Map map = new HashMap();

  public static <T> R<T> success(T object) {
    R<T> r = new R<T>();
    r.data = object;
    r.code = 1;
    return r;
  }

  public static <T> R<T> error(String message) {
    R<T> r = new R<T>();
    r.message = message;
    r.code = 0;
    return r;
  }

  public R<T> add(String key, Object value) {
    this.map.put(key,value);
    return this;
  }
}
