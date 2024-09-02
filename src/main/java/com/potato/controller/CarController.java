package com.potato.controller;

import com.potato.common.R;
import com.potato.controller.dto.requestBody.CarBindingRequestBody;
import com.potato.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
@CrossOrigin
@Slf4j
public class CarController {

  @Autowired
  private CarService carService;

  @PostMapping("/bind")
  public R<String> bind(@RequestBody CarBindingRequestBody request) {
    String plateColor = request.getPlateColor();
    String plateNumber = request.getPlateNumber();
    String brand = request.getBrand();
    String type = request.getType();

    try {
      String result = carService.binding(plateColor, plateNumber, brand, type);
      return R.success(result);
    } catch (Exception e) {
      return R.error(e.getMessage());
    }
  }
}
