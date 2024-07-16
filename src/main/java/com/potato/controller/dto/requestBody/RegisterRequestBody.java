package com.potato.controller.dto.requestBody;

import lombok.Data;

@Data
public class RegisterRequestBody {
  String username;
  String password;
  String confirmedPassword;
  String phone;
  String code;
}
