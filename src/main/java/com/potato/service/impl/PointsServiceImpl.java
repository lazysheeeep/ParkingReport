package com.potato.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.entity.Points;
import com.potato.mapper.PointsMapper;
import com.potato.service.PointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PointsServiceImpl extends ServiceImpl<PointsMapper, Points> implements PointsService {

  @Override
  public void create(String username) {
    Points points = new Points();
    points.setUsername(username);
    points.setSummary(0);
    this.save(points);
  }
}
