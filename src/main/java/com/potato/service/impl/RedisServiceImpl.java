package com.potato.service.impl;

import com.potato.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public void setValueWithExpiration(String key, String value, Long timeout, TimeUnit timeUnit) {
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    ops.set(key, value, timeout, timeUnit);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }
}
