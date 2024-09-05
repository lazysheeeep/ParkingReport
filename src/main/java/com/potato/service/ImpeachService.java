package com.potato.service;

import com.potato.controller.dto.ListInfo;
import com.potato.controller.dto.requestBody.ImpeachRequest;
import com.potato.entity.ImpeachInfo;

import java.util.List;

public interface ImpeachService {

  public String createImpeach(ImpeachRequest request);

  public ListInfo getAllImpeach(int pageNum);

  public ImpeachInfo getById(Long id);

  public String deleteImpeach(Long id);
}
