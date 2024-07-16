package com.potato.controller.dto.requestBody;

import lombok.Data;

@Data
public class UserUpdateRequest {

  String username;

  Character sex;

  Short age;

}
