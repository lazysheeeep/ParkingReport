package com.potato.controller;

import com.potato.common.R;
import com.potato.common.RList;
import com.potato.common.exception.CustomException;
import com.potato.controller.dto.ListInfo;
import com.potato.controller.dto.requestBody.ImpeachRequest;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.controller.dto.responseBody.ListPlateResponseBody;
import com.potato.controller.dto.responseBody.PlateResponseBody;
import com.potato.entity.ImpeachInfo;
import com.potato.service.ImpeachService;
import com.potato.service.RecognizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
  public R<String> create(@RequestBody ImpeachRequest request) {
    try {
      String result = impeachService.createImpeach(request);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @PostMapping("/getPlate")
  public R<ListPlateResponseBody> getPlate(@RequestParam("file") MultipartFile image) {
    if (image == null || image.isEmpty()) {
      return R.error("图片为空");
    }
    try {
      ListPlateResponseBody result = recognizeService.recognizePlate(image);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @GetMapping("/getImage")
  public R<Resource> getImage(@RequestParam("local_path") String localPath) {

    localPath = URLDecoder.decode(localPath, StandardCharsets.UTF_8);

    try {
      File file = new File(localPath);
      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      return R.success(resource);
    } catch (IOException e) {
      return R.error(e.getMessage());
    }
  }

  @PostMapping("/getAll")
  public RList<List<ImpeachInfo>> getAll(@RequestBody PageRequest page) {
    int pageNum = page.getPageNum();
    ListInfo result = impeachService.getAllImpeach(pageNum);
    int total = result.getTotal();
    List<ImpeachInfo> infoList = result.getLists();
    return RList.success(infoList, total);
  }

  @GetMapping("/getById")
  public R<ImpeachInfo> getById(@RequestParam("id") Long id) {
    ImpeachInfo info = impeachService.getById(id);
    return R.success(info);
  }

  @GetMapping("/delete")
  public R<String> deleteImpeach(@RequestParam("id") Long id) {
    try {
      String result = impeachService.deleteImpeach(id);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }
}
