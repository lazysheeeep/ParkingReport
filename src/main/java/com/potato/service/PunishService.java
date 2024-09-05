package com.potato.service;

import com.potato.controller.dto.ListInfo;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.PunishInfo;

import java.util.List;

public interface PunishService {

  public void create(String pUsername, ImpeachInfo impeachInfo);

  public ListInfo get(int pageNum);
}
