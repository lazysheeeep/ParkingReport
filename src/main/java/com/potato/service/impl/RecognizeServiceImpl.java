package com.potato.service.impl;

import com.potato.common.ColorMap;
import com.potato.common.Context;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.CustomException;
import com.potato.config.BaiDuConfig;
import com.potato.controller.dto.responseBody.ListPlateResponseBody;
import com.potato.entity.PlateInfo;
import com.potato.service.RecognizeService;
import com.potato.util.RandomCode;
import com.potato.util.UploadStatic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class RecognizeServiceImpl implements RecognizeService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Autowired
  private BaiDuConfig config;

  public ListPlateResponseBody recognizePlate(MultipartFile file) {

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
      String localPath = filename + rand + file.getOriginalFilename();
      file.transferTo(uploadedFile);

      UploadStatic uploadStatic = new UploadStatic();
      String staticPath = uploadStatic.uploadFile(file);

      List<PlateInfo> temp = config.verifyPlate(localPath);

      ListPlateResponseBody result = new ListPlateResponseBody();
      result.setPlateList(temp);
      result.setStaticPath("http://localhost:8080/" + staticPath);
      result.setTotal(temp.size());

      return result;

    } catch (Exception e) {
      errorCode = "3001";
      errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(e.getMessage());
    }
  }
}
