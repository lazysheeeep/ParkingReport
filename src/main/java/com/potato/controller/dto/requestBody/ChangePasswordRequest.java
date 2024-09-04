package com.potato.controller.dto.requestBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChangePasswordRequest {

  private String username;

  private String phone;

  private String code;

  private String password;

  @JsonProperty("confirmed_password")
  private String confirmedPassword;
}
