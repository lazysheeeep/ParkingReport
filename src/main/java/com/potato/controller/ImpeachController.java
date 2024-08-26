package com.potato.controller;

import com.potato.common.R;
import com.potato.common.exception.CustomException;
import com.potato.entity.ImpeachInfo;
import com.potato.service.ImpeachService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/impeach")
@CrossOrigin
@Slf4j
public class ImpeachController {

  @Autowired
  private ImpeachService impeachService;

  @PostMapping("/create")
  public R<String> create(@RequestBody ImpeachInfo info) {
    try {
      String result = impeachService.createImpeach(info);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @GetMapping("/getAll")
  public R<ImpeachInfo> getAll() {
    R response = new R<>();
    List<ImpeachInfo> infoList = impeachService.getAllImpeach();
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

  @PostMapping("/pass")
  public R<String> passImpeach(@RequestParam("id") Long id) {
    String result = impeachService.passImpeach(id);
    return R.success(result);
  }
}
