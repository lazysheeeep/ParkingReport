package com.potato.config;

import com.baidu.aip.ocr.AipOcr;
import com.potato.common.ColorMap;
import com.potato.common.exception.CustomException;
import com.potato.entity.PlateInfo;
import org.hibernate.validator.constraints.time.DurationMax;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Configuration
@Slf4j
public class BaiDuConfig {

  //这个为什么放这里不放yaml呢因为他总说我传入的key是空
  public static final String APP_ID = "112098015";
  public static final String API_KEY = "y20mrH8SVahwfvGOLU22jPSo";
  public static final String SECRET_KEY = "l4CQUdbSbTjKZsDm8QrtGGbeKhEIa1Oe";

  private final ColorMap colorMap = new ColorMap();

  public List<PlateInfo> verifyPlate(String path) {
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("multi_detect", "true");

    JSONObject res = client.plateLicense(path, options);

    if (res.has("words_result")) {

      int total = res.getJSONArray("words_result").length();
      List<PlateInfo> result = new LinkedList<>();

      for (int i = 0; i < total; i++) {

        PlateInfo info = new PlateInfo();
        String color = res.getJSONArray("words_result").getJSONObject(i).getString("color");
        String number = res.getJSONArray("words_result").getJSONObject(i).getString("number");
        info.setColor(colorMap.getChineseColor(color));
        info.setNumber(number);
        result.add(info);

      }

      return result;
    } else {
      throw new CustomException(("车牌识别失败！"));
    }

  }

}
