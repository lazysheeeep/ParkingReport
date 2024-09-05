package com.potato.util;

import com.potato.common.Context;
import com.potato.common.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadStatic {

  private final Path rootLocation = Paths.get("uploads");

  public String uploadFile(MultipartFile file) {

    try {

      Path userFolder = this.rootLocation.resolve(Context.getCurrentUser().getUsername());
      if (!Files.exists(userFolder)) {
        Files.createDirectories(userFolder);
      }

      // 保存文件到用户文件夹
      Path filePath = userFolder.resolve(file.getOriginalFilename());
      int count = 1;
      String newFileName = null;
      while (Files.exists(filePath)) {
        newFileName = "(" + count + ")" + file.getOriginalFilename();
        filePath = userFolder.resolve(newFileName);
        count++;
      }
      Files.copy(file.getInputStream(), filePath);

      return "uploads/" + Context.getCurrentUser().getUsername() + "/" + newFileName;
    } catch (IOException e) {

      throw new CustomException(e.getMessage());

    }
  }
}
