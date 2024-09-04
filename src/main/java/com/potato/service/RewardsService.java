package com.potato.service;

import com.potato.entity.Rewards;

import java.util.List;

public interface RewardsService {

  public String create(Long impeachId);

  public List<Rewards> get(int pageNum);
}
