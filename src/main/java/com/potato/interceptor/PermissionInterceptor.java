package com.potato.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.potato.common.Context;
import com.potato.common.R;
import com.potato.common.exception.CustomException;
import com.potato.common.exception.TokenErrorException;
import com.potato.common.exception.TokenExpireException;
import com.potato.controller.dto.UserBaseInfo;
import com.potato.util.JwtUtil;
import com.tencentcloudapi.billing.v20180709.models.JsonObject;
import io.jsonwebtoken.Claims;
import io.lettuce.core.dynamic.annotation.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
  // 先不写拦截器了 直接测试接口
  // 还是需要拦截器 写吧
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws IOException {

    //如果请求为OPTIONS,则放行
    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      log.info("*****OPTIONS请求，放行*****");
      return true;
    }

    //检查用户token
    String token = request.getHeader("Authorization");

    if (token == null) {
      log.info("该请求没有携带token");
    }

    //校验token并且取出其中信息
    try {
      Claims claim = JwtUtil.parseToken(token);
      Long userId = (Long) claim.get("userId");
      String userName = claim.getSubject();
      String status = claim.get("status").toString();

      if (status.equals("0")) {

        sendErrorResponse(response,"用户已被禁用");
        return false;

      }

      //jwt校验合格后,其存储的信息会加入请求头部，不合格的请求头部用户为空
      Context.setCurrentId(userId);
      UserBaseInfo userInfo = new UserBaseInfo();
      userInfo.setId(userId);
      userInfo.setUsername(userName);
      userInfo.setStatus(status);
      Context.setCurrentUser(userInfo);

      return true;
    } catch (TokenErrorException e) {

      sendErrorResponse(response,"令牌错误");

      return false;
    } catch (TokenExpireException e) {

      sendErrorResponse(response,"令牌已过期");

      return false;
    }
  }


  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, e);
  }

  private void addResBody(HttpServletResponse response, R res) throws IOException {

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.write(JSONObject.toJSONString(res));
    out.flush();
    out.close();
  }

  public void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=UTF-8");

    PrintWriter writer = response.getWriter();
    writer.write(JSONObject.toJSONString(new R<>(0,message,null,null)));
    writer.flush();
    writer.close();
  }
}
