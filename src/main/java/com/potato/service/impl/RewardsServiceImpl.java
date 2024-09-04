package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.Rewards;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.mapper.RewardsMapper;
import com.potato.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RewardsServiceImpl extends ServiceImpl<RewardsMapper, Rewards> implements RewardsService {

  @Autowired
  private ImpeachInfoMapper impeachInfoMapper;

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
}
