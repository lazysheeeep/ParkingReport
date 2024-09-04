package com.potato.service;

import com.potato.entity.ImpeachInfo;
import com.potato.entity.PunishInfo;

import java.util.List;

public interface PunishService {

  public void create(String pUsername, ImpeachInfo impeachInfo);

  public List<PunishInfo> get(int pageNum);
}
