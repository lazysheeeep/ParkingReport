package com.potato.service.impl;

import com.potato.config.SmsConfig;
import com.potato.service.RedisService;
import com.potato.service.SmsService;
import com.potato.util.RandomCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

  @Autowired
  private SmsConfig smsConfig;

  @Autowired
  private RedisService redisService;

  public boolean send(String phone) {
    if (StringUtils.isEmpty(phone)) {
      return false;
    }
    try {
      String code = RandomCode.randomCode();
      smsConfig.sendMessage(phone, code);
      redisService.setValueWithExpiration(phone,code, 5L, TimeUnit.MINUTES);
    } catch (Exception e) {
      log.info("failed to send message:{}",e.getMessage());
      return false;
    }
    return true;
  }

}
