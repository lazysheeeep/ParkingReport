package com.potato.common;

import java.util.HashMap;

public class ColorMap {

  private HashMap<String,String> colorMap = new HashMap<String,String>();

  public ColorMap() {
    colorMap.put("blue","蓝色");
    colorMap.put("green","绿色");
    colorMap.put("yellow","黄色");
  }

  public String getChineseColor(String color) {
    return colorMap.get(color);
  }
}
