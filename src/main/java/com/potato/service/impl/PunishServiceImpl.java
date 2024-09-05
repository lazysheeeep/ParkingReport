package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.controller.dto.ListInfo;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.PunishInfo;
import com.potato.entity.Rewards;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.mapper.PunishInfoMapper;
import com.potato.service.PunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PunishServiceImpl extends ServiceImpl<PunishInfoMapper, PunishInfo> implements PunishService {

  @Autowired
  private PunishInfoMapper punishInfoMapper;

  public void create(String pUsername, ImpeachInfo impeachInfo) {

    PunishInfo punishInfo = new PunishInfo();

    punishInfo.setPUsername(pUsername);
    punishInfo.setImpeachId(impeachInfo.getId());
    punishInfo.setImpeachTime(impeachInfo.getCreatedAt());
    punishInfo.setDescription(impeachInfo.getDescription());
    punishInfo.setLocation(impeachInfo.getLocation());
    punishInfo.setPassTime(LocalDateTime.now());

    this.save(punishInfo);
  }

  public ListInfo get(int pageNum) {

    LambdaQueryWrapper<PunishInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(PunishInfo::getPUsername,Context.getCurrentUser().getUsername());
    int total = punishInfoMapper.selectList(queryWrapper).size();

    queryWrapper.orderByDesc(PunishInfo::getPassTime);
    Page<PunishInfo> page = new Page<>(pageNum,2);
    IPage<PunishInfo> resultPage = punishInfoMapper.selectPage(page,queryWrapper);
    List<PunishInfo> infoList = resultPage.getRecords();

    ListInfo result = new ListInfo();
    result.setTotal(total);
    result.setLists(infoList);

    return result;
  }
}
