package com.potato.service;

import com.potato.entity.ImpeachInfo;

import java.util.List;

public interface AdminService {

  public List<ImpeachInfo> getAllImpeach(int pageNum);

  public List<ImpeachInfo> getUnTreatedImpeach(int pageNum);

  public List<ImpeachInfo> getTreatedImpeach(int pageNum);

  public String passImpeach(Long id);

  public String vetoImpeach(Long id);
}
