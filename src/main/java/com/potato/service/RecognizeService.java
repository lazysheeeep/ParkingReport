package com.potato.service;

import com.potato.controller.dto.responseBody.ListPlateResponseBody;
import org.springframework.web.multipart.MultipartFile;

public interface RecognizeService {

  ListPlateResponseBody recognizePlate(MultipartFile file);

}
