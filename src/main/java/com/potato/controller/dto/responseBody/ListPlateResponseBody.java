package com.potato.controller.dto.responseBody;

import com.potato.entity.PlateInfo;
import lombok.Data;

import java.util.List;

@Data
public class ListPlateResponseBody {

  private int total;

  private List<PlateInfo> plateList;

  private String staticPath;
}
