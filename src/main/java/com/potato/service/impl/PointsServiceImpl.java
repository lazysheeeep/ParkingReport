package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.entity.Points;
import com.potato.mapper.PointsMapper;
import com.potato.service.PointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PointsServiceImpl extends ServiceImpl<PointsMapper, Points> implements PointsService {

  @Autowired
  private PointsMapper pointsMapper;

  @Override
  public void create(String username) {
    Points points = new Points();
    points.setUsername(username);
    points.setSummary(0);
    this.save(points);
  }

  @Override
  public int get(String username) {
    LambdaQueryWrapper<Points> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Points::getUsername,username);
    Points result = this.getOne(queryWrapper);
    return result.getSummary();
  }

  @Override
  public void add(String username) {
    LambdaQueryWrapper<Points> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Points::getUsername,username);
    Points result = this.getOne(queryWrapper);
    result.setSummary(result.getSummary() + 5);
    pointsMapper.update(result,queryWrapper);
  }
}
