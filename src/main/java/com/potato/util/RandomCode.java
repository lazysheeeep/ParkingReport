package com.potato.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RandomCode {
  public static String randomCode() {
    Random random = new Random();
    int codeLength = 6;
    StringBuilder sb = new StringBuilder(codeLength);
    for (int i = 0; i < codeLength; i++) {
      int randomDigit = random.nextInt(10);
      sb.append(randomDigit);
    }

    return sb.toString();
  }

}
