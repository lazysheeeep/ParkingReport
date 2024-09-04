package com.potato.controller;

import com.potato.common.R;
import com.potato.common.exception.CustomException;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.controller.dto.responseBody.PlateResponseBody;
import com.potato.entity.ImpeachInfo;
import com.potato.service.ImpeachService;
import com.potato.service.RecognizeService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/impeach")
@CrossOrigin
@Slf4j
public class ImpeachController {

  @Autowired
  private ImpeachService impeachService;

  @Autowired
  private RecognizeService recognizeService;

  @PostMapping("/create")
  public R<String> create(@RequestBody ImpeachInfo info) {
    try {
      String result = impeachService.createImpeach(info);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @PostMapping("/getPlate")
  public R<PlateResponseBody> getPlate(@RequestParam("image")MultipartFile image) {
    if (image.isEmpty()) {
      return R.error("图片为空");
    }
    try {
      String[] result = recognizeService.recognizePlate(image);
      PlateResponseBody plateResponseBody = new PlateResponseBody();
      plateResponseBody.setColor(result[0]);
      plateResponseBody.setPlateNumber(result[1]);
      return R.success(plateResponseBody);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @GetMapping("/getAll")
  public R<ImpeachInfo> getAll(@RequestBody PageRequest page) {
    R response = new R<>();
    int pageNum = page.getPageNum();
    List<ImpeachInfo> infoList = impeachService.getAllImpeach(pageNum);
    for (ImpeachInfo info : infoList) {
      response.add(String.valueOf(info.getId()),info);
    }
    response.setMessage("举报信息如下：");
    response.setCode(1);
    return response;
  }

  @GetMapping("/getById")
  public R<ImpeachInfo> getById(@RequestParam("id") Long id) {
    ImpeachInfo info = impeachService.getById(id);
    return R.success(info);
  }

  @PostMapping("/delete")
  public R<String> deleteImpeach(@RequestParam("id") Long id) {
    try {
      String result = impeachService.deleteImpeach(id);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }
}
