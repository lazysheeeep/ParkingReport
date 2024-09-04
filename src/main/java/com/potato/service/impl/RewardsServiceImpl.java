package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.Rewards;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.mapper.RewardsMapper;
import com.potato.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardsServiceImpl extends ServiceImpl<RewardsMapper, Rewards> implements RewardsService {

  @Autowired
  private ImpeachInfoMapper impeachInfoMapper;

  @Autowired
  private RewardsMapper rewardsMapper;

  public String create(Long impeachId) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getId,impeachId);
    ImpeachInfo impeachInfo = impeachInfoMapper.selectOne(queryWrapper);

    Rewards rewards = new Rewards();

    rewards.setUsername(impeachInfo.getIUsername());
    rewards.setImpeachId(impeachId);
    rewards.setImpeachTime(impeachInfo.getCreatedAt());
    rewards.setAddTime(LocalDateTime.now());
    rewards.setAddPoints(5);

    this.save(rewards);

    return impeachInfo.getIUsername();
  }

  public List<Rewards> get(int pageNum) {

    LambdaQueryWrapper<Rewards> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Rewards::getUsername, Context.getCurrentUser().getUsername());
    queryWrapper.orderByDesc(Rewards::getAddTime);
    Page<Rewards> page = new Page<>(pageNum,2);
    IPage<Rewards> resultPage = rewardsMapper.selectPage(page, queryWrapper);

    List<Rewards> result = resultPage.getRecords();

    return result;
  }
}
