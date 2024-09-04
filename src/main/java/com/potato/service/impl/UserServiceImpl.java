package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.common.exception.CustomException;
import com.potato.common.ErrorCodeMap;
import com.potato.controller.dto.UserBaseInfo;
import com.potato.entity.ImpeachInfo;
import com.potato.entity.User;
import com.potato.mapper.UserMapper;
import com.potato.service.PointsService;
import com.potato.service.RedisService;
import com.potato.service.SmsService;
import com.potato.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private SmsService smsService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private PointsService pointsService;

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Override
  public void register(String username, String password, String confirmedPassword, String phone, String code) {

    String errorCode;

    if (!password.equals(confirmedPassword)) {
      errorCode = "1001";
      String errorMessage = errorCodeMap.getErrorMessage(errorCode);
      log.info(errorMessage);
      throw new CustomException(errorMessage);
    }

    if (!code.equals(redisService.getValue(phone))) {
      errorCode = "1002";
      String errorMessage = errorCodeMap.getErrorMessage(errorCode);
      log.info(errorMessage);
      throw new CustomException(errorMessage);
    }

    LambdaQueryWrapper<User> queryWrapper =  new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getUsername,username);
    User user1 = this.getOne(queryWrapper);
    queryWrapper.eq(User::getPhone,phone);
    User user2 = this.getOne(queryWrapper);
    if (user1 == null && user2 == null) {
      User user = new User();
      user.setUsername(username);
      user.setPhone(phone);
      user.setAvatar("default");
      user.setPassword(password);
      user.setRoleId("001");
      user.setStatus("1");
      this.save(user);
      pointsService.create(username);
    } else {
      log.info("用户名或手机号已被使用，请更换");
      throw new CustomException("用户名或手机号已被使用，请更换");
    }
  }

  @Override
  public User login(String username, String password) {

    String errorCode;

    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getUsername, username);
    queryWrapper.eq(User::getPassword,password);
    User exist = this.getOne(queryWrapper);
    if (exist == null) {
      errorCode = "1003";
      String errorMessage = errorCodeMap.getErrorMessage(errorCode);
      log.info(errorMessage);
      throw new CustomException(errorMessage);
    } else {
      return exist;
    }
  }

  @Override
  @Transactional
  public String updateMessage(User user) {
    String errorCode;
    Long userId = Context.getCurrentId();
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getId, userId);
    // 通过该用户id找出该用户
    UserBaseInfo realUser = Context.getCurrentUser();
    String status = Context.getCurrentUser().getStatus();
    String userName = Context.getCurrentUser().getUsername();

    user.setId(userId);
    user.setStatus(status);

    // 修改了用户名
    if (!user.getUsername().equals(userName)) {
      // 输入了空
      if (user.getUsername().isEmpty()) {
        throw new CustomException("用户名不能为空");
      }
      LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
      queryWrapper1.eq(User::getUsername, user.getUsername());
      User temp = this.getOne(queryWrapper1);
      if (temp != null) {
        throw new CustomException("用户名已存在");
      }
    }
    this.updateById(user);
    return "修改个人信息成功！";
  }

}
