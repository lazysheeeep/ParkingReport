package com.potato.service;

import org.springframework.web.multipart.MultipartFile;

public interface RecognizeService {

  String recognizePlate(MultipartFile file);

}
