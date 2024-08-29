package com.potato.service;

import com.potato.entity.ImpeachInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImpeachService {

  public String createImpeach(ImpeachInfo info);

  public List<ImpeachInfo> getAllImpeach();

  public ImpeachInfo getById(Long id);

  public String passImpeach(Long id);
}
