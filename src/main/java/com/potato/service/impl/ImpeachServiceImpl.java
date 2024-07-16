package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.CustomException;
import com.potato.entity.ImpeachInfo;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.service.ImpeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ImpeachServiceImpl extends ServiceImpl<ImpeachInfoMapper,ImpeachInfo> implements ImpeachService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Autowired
  private ImpeachInfoMapper mapper;

  @Override
  public String createImpeach(ImpeachInfo info) {

    String errorCode;
    String errorMessage;

    if (info.getPlateNumber().isEmpty()) {
      errorCode = "2001";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }

    if (info.getLocation().isEmpty()) {
      errorCode = "2002";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }

    info.setCreatedAt(LocalDateTime.now());
    this.save(info);
    return "举报成功！";
  }

  public List<ImpeachInfo> getAllImpeach() {

    String username = Context.getCurrentUser().getUsername();
    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getIUsername,username);
    List<ImpeachInfo> infoList = mapper.selectList(queryWrapper);
    if (infoList.isEmpty()) {
      return null;
    }
    return infoList;
  }

  public ImpeachInfo getById(Long id) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getId,id);
    return this.getOne(queryWrapper);

  }

}
