package com.potato;

import com.baidu.aip.ocr.AipOcr;

import com.potato.config.BaiDuConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;


//@RunWith(SpringRunner.class)
//@SpringBootTest
class DemoApplicationTests {

  @Test
  public static void main(String[] args) throws JSONException {


//    // 初始化一个AipOcr
//    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//    // 调用接口
    String path = "src/main/resources/picture/chars_recognise_huAGH092.jpg";
//
//    sample(client,path);

//    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//    HashMap<String, String> options = new HashMap<String, String>();
//    options.put("multi_detect", "true");
//
//    JSONObject res = client.plateLicense(path, options);
//
//    System.out.println(res.toString());
    BaiDuConfig config = new BaiDuConfig();

    System.out.println(config.verifyPlate(path));

  }


  public static void sample(AipOcr client,String path) throws JSONException {
    // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("multi_detect", "true");


    // 参数为本地图片路径
    JSONObject res = client.plateLicense(path, options);
    System.out.println(res.toString(2));

  }

}
