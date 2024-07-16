package com.potato.common;

import com.potato.controller.dto.UserBaseInfo;

public class Context {
  private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

  private static ThreadLocal<UserBaseInfo> userThreadLocal = new ThreadLocal<>();

  public static void setCurrentId(long id) {
    threadLocal.set(id);
  }

  public static Long getCurrentId() {
    return threadLocal.get();
  }

  public static void setCurrentUser(UserBaseInfo user) {
    userThreadLocal.set(user);
  }

  public static UserBaseInfo getCurrentUser() {
    return userThreadLocal.get();
  }

}
