package com.potato.controller.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CarBindingRequestBody {

  @NotNull(message = "车牌颜色不能为空")
  private String plateColor;

  @NotNull(message = "车牌号码不能为空")
  private String plateNumber;

  @NotNull(message = "车辆品牌不能为空")
  private String brand;

  @NotNull(message = "车牌型号不能为空")
  private String type;
}
