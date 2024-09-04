package com.potato.controller.dto.requestBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImpeachRequest {

  @JsonProperty("i_phone")
  private String iPhone;

  @JsonProperty("image_path")
  private String imagePath;

  private String location;

  private String description;

  @JsonProperty("plate_color")
  private String plateColor;

  @JsonProperty("plate_number")
  private String plateNumber;

}
