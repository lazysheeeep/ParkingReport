package com.potato.config;

import com.baidu.aip.ocr.AipOcr;
import com.potato.common.exception.CustomException;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@Slf4j
public class BaiDuConfig {

  //这个为什么放这里不放yaml呢因为他总说我传入的key是空
  public static final String APP_ID = "112098015";
  public static final String API_KEY = "y20mrH8SVahwfvGOLU22jPSo";
  public static final String SECRET_KEY = "l4CQUdbSbTjKZsDm8QrtGGbeKhEIa1Oe";

  public String[] verifyPlate(String path) {
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("multi_detect", "true");

    JSONObject res = client.plateLicense(path, options);

    if (res.has("words_result")) {
      String color = res.getJSONArray("words_result").getJSONObject(0).getString("color");
      String number = res.getJSONArray("words_result").getJSONObject(0).getString("number");

      String[] result = new String[2];
      result[0] = color;
      result[1] = number;

      return result;
    } else {

      throw new CustomException(("车牌识别失败！"));
    }

  }

}
