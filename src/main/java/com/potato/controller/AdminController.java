package com.potato.controller;

import com.potato.common.R;
import com.potato.common.RList;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.entity.ImpeachInfo;
import com.potato.service.AdminService;
import com.potato.service.PointsService;
import com.potato.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@Slf4j
public class AdminController {

  @Autowired
  private AdminService adminService;

  @Autowired
  private RewardsService rewardsService;

  @Autowired
  private PointsService pointsService;

  @GetMapping("/getAll")
  public RList<List<ImpeachInfo>> getAllImpeach(@RequestBody PageRequest pageRequest) {

    List<ImpeachInfo> result = adminService.getAllImpeach(pageRequest.getPageNum());

    int total = result.size();

    return RList.success(result,total);
  }

  @GetMapping("/getUnTreated")
  public RList<List<ImpeachInfo>> getUnTreated(@RequestBody PageRequest pageRequest) {

    List<ImpeachInfo> result = adminService.getUnTreatedImpeach(pageRequest.getPageNum());

    int total = result.size();

    return RList.success(result,total);
  }

  @PostMapping("/pass")
  @Transactional
  public R<String> passImpeach(@RequestParam("id") Long id) {
    String result = adminService.passImpeach(id);
    String username = rewardsService.create(id);
    pointsService.add(username);

    return R.success(result);
  }
}
