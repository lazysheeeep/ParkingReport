package com.potato.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.potato.controller.dto.requestBody.ChangePasswordRequest;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.User;

/**
 * 用户服务层接口.
 */
public interface UserService extends IService<User> {

  void register(String username, String password, String confirmPassword, String phone, String code);

  User login(String username, String password);

  String updateMessage(User user);

  String changePassword(ChangePasswordRequest request);

}
