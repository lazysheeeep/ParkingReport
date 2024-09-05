package com.potato.service;

import com.potato.controller.dto.ListInfo;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.Rewards;

import java.util.List;

public interface RewardsService {

  public void create(ImpeachInfo impeachInfo);

  public ListInfo get(int pageNum);
}
