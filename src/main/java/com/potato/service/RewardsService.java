package com.potato.service;

import com.potato.entity.ImpeachInfo;
import com.potato.entity.Rewards;

import java.util.List;

public interface RewardsService {

  public void create(ImpeachInfo impeachInfo);

  public List<Rewards> get(int pageNum);
}
