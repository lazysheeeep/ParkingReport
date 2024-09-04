package com.potato.service.impl;

import com.potato.common.Context;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.CustomException;
import com.potato.config.BaiDuConfig;
import com.potato.service.RecognizeService;
import com.potato.util.RandomCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class RecognizeServiceImpl implements RecognizeService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Autowired
  private BaiDuConfig config;

  public String[] recognizePlate(MultipartFile file) {

    String errorCode;
    String errorMessage;

    try {
      // Ensure the upload directory exists
      File uploadDir = new File("C:\\Users\\lenovo\\Desktop\\code\\uploads\\" + Context.getCurrentUser().getId());

      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }

      // Save the file locally
      String filename = "C:\\Users\\lenovo\\Desktop\\code\\uploads\\"
              + Context.getCurrentUser().getId()
              + "\\";

      String rand = RandomCode.randomCode();

      File uploadedFile = new File(filename,rand + file.getOriginalFilename());

      file.transferTo(uploadedFile);

      String localPath = filename + rand + file.getOriginalFilename();

      String[] temp = config.verifyPlate(localPath);

      String[] result = new String[3];
      result[0] = temp[0];
      result[1] = temp[1];
      result[2] = localPath;

      if (result[0] == null || result[1] == null) {
        errorCode = "3002";
        errorMessage = errorCodeMap.getErrorMessage(errorCode);
        throw new CustomException(errorMessage);
      }

      return result;

    } catch (IOException e) {
      errorCode = "3001";
      errorCodeMap.getErrorMessage(errorCode);
      throw new RuntimeException(e);
    }
  }
}
