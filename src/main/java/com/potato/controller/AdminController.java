package com.potato.controller;

import com.potato.common.R;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.entity.ImpeachInfo;
import com.potato.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@Slf4j
public class AdminController {

  @Autowired
  private AdminService adminService;

  @GetMapping("/getAll")
  public R<List<ImpeachInfo>> getAllImpeach(@RequestBody PageRequest pageRequest) {

    List<ImpeachInfo> result = adminService.getAllImpeach(pageRequest.getPageNum());

    return R.success(result);
  }

  @GetMapping("/getUnTreated")
  public R<List<ImpeachInfo>> getUnTreated(@RequestBody PageRequest pageRequest) {
    List<ImpeachInfo> result = adminService.getUnTreatedImpeach(pageRequest.getPageNum());

    return R.success(result);
  }

  @PostMapping("/pass")
  public R<String> passImpeach(@RequestParam("id") Long id) {
    String result = adminService.passImpeach(id);
    return R.success(result);
  }
}
