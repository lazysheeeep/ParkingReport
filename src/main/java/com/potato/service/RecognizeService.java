package com.potato.service;

import org.springframework.web.multipart.MultipartFile;

public interface RecognizeService {

  public String recognizePlate(MultipartFile file);

}
