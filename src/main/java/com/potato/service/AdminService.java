package com.potato.service;

import com.potato.controller.dto.ListInfo;
import com.potato.entity.ImpeachInfo;

import java.util.List;

public interface AdminService {

  public ListInfo getAllImpeach(int pageNum);

  public ListInfo getUnTreatedImpeach(int pageNum);

  public ListInfo getTreatedImpeach(int pageNum);

  public String passImpeach(Long id);

  public String vetoImpeach(Long id);
}
