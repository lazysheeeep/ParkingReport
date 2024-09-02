package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.CustomException;
import com.potato.entity.Car;
import com.potato.mapper.CarMapper;
import com.potato.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  public String binding(String plateColor, String plateNumber, String brand, String type) {

    String errorCode;
    String errorMessage;

    LambdaQueryWrapper<Car> carMapper = new LambdaQueryWrapper<>();

    carMapper.eq(Car::getPlateNumber,plateNumber);

    Car exist = this.getOne(carMapper);
    if (exist == null) {
      Car curr = new Car();
      curr.setUserId(Context.getCurrentUser().getId());
      curr.setPlateColor(plateColor);
      curr.setPlateNumber(plateNumber);
      curr.setBrand(brand);
      curr.setType(type);
      this.save(curr);
      return "绑定成功!";
    } else {
      errorCode = "4001";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }
  }
}
