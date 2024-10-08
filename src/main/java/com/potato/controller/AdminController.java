package com.potato.controller;

import com.potato.common.R;
import com.potato.common.RList;
import com.potato.controller.dto.ListInfo;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.entity.ImpeachInfo;
import com.potato.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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

  @Autowired
  private ImpeachService impeachService;

  @Autowired
  private PunishService punishService;

  @Autowired
  private CarService carService;

  @PostMapping("/getAll")
  public RList<List<ImpeachInfo>> getAllImpeach(@RequestBody PageRequest pageRequest) {

    ListInfo result = adminService.getAllImpeach(pageRequest.getPageNum());
    int total = result.getTotal();
    List<ImpeachInfo> infoList = result.getLists();

    return RList.success(infoList, total);
  }

  @PostMapping("/getUnTreated")
  public RList<List<ImpeachInfo>> getUnTreated(@RequestBody PageRequest pageRequest) {

    ListInfo result = adminService.getUnTreatedImpeach(pageRequest.getPageNum());

    int total = result.getTotal();
    List<ImpeachInfo> infoList = result.getLists();

    return RList.success(infoList, total);
  }

  @PostMapping("/getTreated")
  public RList<List<ImpeachInfo>> getTreated(@RequestBody PageRequest pageRequest) {

    ListInfo result = adminService.getTreatedImpeach(pageRequest.getPageNum());

    int total = result.getTotal();
    List<ImpeachInfo> infoList = result.getLists();

    return RList.success(infoList, total);

  }

  @GetMapping("/getById")
  public R<ImpeachInfo> getById(@RequestParam("id")Long id) {
    ImpeachInfo info = impeachService.getById(id);
    return R.success(info);
  }

  @GetMapping("/pass")
  @Transactional
  public R<String> passImpeach(@RequestParam("id") Long id) {
    try {
      String result = adminService.passImpeach(id);
      ImpeachInfo temp = impeachService.getById(id);
      rewardsService.create(temp);
      pointsService.add(temp.getIUsername());
      String pUsername = carService.getUserByNumber(temp.getPlateColor(),temp.getPlateNumber());
      punishService.create(pUsername,temp);

      return R.success(result);
    } catch (Exception e) {

      return R.error(e.getMessage());

    }
  }

  @GetMapping("/veto")
  public R<String> vetoImpeach(@Param("id") Long id) {
    String result = adminService.vetoImpeach(id);
    return R.success(result);
  }
}
