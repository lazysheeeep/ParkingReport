package com.potato.controller;

import com.potato.common.Context;
import com.potato.common.RList;
import com.potato.common.exception.CustomException;
import com.potato.common.R;
import com.potato.controller.dto.requestBody.LoginRequestBody;
import com.potato.controller.dto.requestBody.PageRequest;
import com.potato.controller.dto.requestBody.RegisterRequestBody;
import com.potato.controller.dto.responseBody.UserResponseBody;
import com.potato.entity.Rewards;
import com.potato.entity.User;
import com.potato.service.PointsService;
import com.potato.service.RewardsService;
import com.potato.service.SmsService;
import com.potato.service.UserService;
import com.potato.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SmsService smsService;

  @Autowired
  private PointsService pointsService;

  @Autowired
  private RewardsService rewardsService;

  @PostMapping("/register")
  public R<String> register(@RequestBody RegisterRequestBody registerRequestBody) {
    String userName = registerRequestBody.getUsername();
    String password = registerRequestBody.getPassword();
    String confirmedPassword = registerRequestBody.getConfirmedPassword();
    String phone = registerRequestBody.getPhone();
    String code = registerRequestBody.getCode();
    try {
      userService.register(userName, password, confirmedPassword, phone, code);
      return R.success("注册成功，即将返回登录界面...");
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @GetMapping("/send")
  public R<String> sendCode(@RequestParam("phone") String phone) {
    boolean flag = smsService.send(phone);
    if (flag) {
      return R.success("验证码发送成功");
    } else {
      return R.error("发送验证码失败");
    }
  }

  @PostMapping(value = "/login")
  public R<UserResponseBody> login(@RequestBody LoginRequestBody loginRequestBody, HttpServletResponse response) {
    String username = loginRequestBody.getUsername();
    String password = loginRequestBody.getPassword();
    try {
      //这里是Service层的登录函数
      User user = userService.login(username, password);
      String token = JwtUtil.generateToken(username, user.getId(),user.getStatus(), user.getRoleId());
      response.addHeader("Authorization", token);
      UserResponseBody responseBody = new UserResponseBody();
      responseBody.setUserId(user.getId());
      responseBody.setUserName(user.getUsername());
      responseBody.setPhone(user.getPhone());
      responseBody.setSex(user.getSex());
      responseBody.setAvatar(user.getAvatar());
      responseBody.setAge(user.getAge());
      responseBody.setRoleId(user.getRoleId());
      return R.success(responseBody);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @PostMapping("/updateUser")
  public R<String> updateUser(@RequestBody User user) {
    try {
      String result = userService.updateMessage(user);
      return R.success(result);
    } catch (CustomException e) {
      return R.error(e.getMessage());
    }
  }

  @GetMapping("/getPoints")
  public R<Integer> getPoint() {
    int result = pointsService.get(Context.getCurrentUser().getUsername());
    return R.success(result);
  }

  @GetMapping("/getRewards")
  public RList<List<Rewards>> getRewards(@RequestBody PageRequest page) {

    List<Rewards> result = rewardsService.get(page.getPageNum());

    int total = result.size();

    return RList.success(result,total);
  }
}
