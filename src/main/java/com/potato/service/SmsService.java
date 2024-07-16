package com.potato.service;

public interface SmsService {
  boolean send(String phone);

  String randomCode();
}
