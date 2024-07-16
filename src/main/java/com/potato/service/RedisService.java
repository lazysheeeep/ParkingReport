package com.potato.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

  void setValueWithExpiration(String key, String value, Long timeout, TimeUnit timeUnit);

  String getValue(String key);

}
