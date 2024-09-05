package com.potato.util;

import com.potato.common.Context;
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
      while (Files.exists(filePath)) {
        String newFileName = "(" + count + ")" + file.getOriginalFilename();
        filePath = userFolder.resolve(newFileName);
        count++;
      }
      Files.copy(file.getInputStream(), filePath);

      return "uploads/" + Context.getCurrentUser().getUsername() + "/" + file.getOriginalFilename();
    } catch (IOException e) {
      e.printStackTrace();
      return "redirect:/uploadFailure";
    }
  }
}
