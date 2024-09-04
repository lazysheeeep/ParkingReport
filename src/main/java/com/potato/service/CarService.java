package com.potato.service;

public interface CarService {

  public String binding(String plateColor, String plateNumber, String brand, String type);

  public String getUserByNumber(String plateColor,String plateNumber);

}
