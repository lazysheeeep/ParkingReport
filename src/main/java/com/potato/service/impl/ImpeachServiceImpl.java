package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.potato.common.Context;
import com.potato.common.ErrorCodeMap;
import com.potato.common.exception.CustomException;
import com.potato.controller.dto.ListInfo;
import com.potato.controller.dto.requestBody.ImpeachRequest;
import com.potato.entity.ImpeachInfo;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.service.ImpeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpeachServiceImpl extends ServiceImpl<ImpeachInfoMapper,ImpeachInfo> implements ImpeachService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Autowired
  private ImpeachInfoMapper impeachInfoMapper;

  @Override
  public String createImpeach(ImpeachRequest request) {

    String errorCode;
    String errorMessage;

    if (request.getPlateNumber().isEmpty()) {
      errorCode = "2001";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }

    if (request.getLocation().isEmpty()) {
      errorCode = "2002";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }

    ImpeachInfo info = new ImpeachInfo();

    info.setIUsername(Context.getCurrentUser().getUsername());
    info.setIPhone(request.getIPhone());
    info.setCreatedAt(LocalDateTime.now());
    info.setDescription(request.getDescription());
    info.setPlateColor(request.getPlateColor());
    info.setPlateNumber(request.getPlateNumber());
    info.setLocation(request.getLocation());
    info.setImagePath(request.getImagePath());
    info.setProcessId(1);
    this.save(info);

    return "举报成功！";
  }



  public ListInfo getAllImpeach(int pageNum) {

    Page<ImpeachInfo> page = new Page<>(pageNum,2);
    String username = Context.getCurrentUser().getUsername();

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getIUsername,username);
    int total = impeachInfoMapper.selectList(queryWrapper).size();

    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page,queryWrapper);
    List<ImpeachInfo> infoList = resultPage.getRecords();

    if (infoList.isEmpty()) {
      return null;
    }

    ListInfo result = new ListInfo();
    result.setTotal(total);
    result.setLists(infoList);

    return result;
  }

  public ImpeachInfo getById(Long id) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getId,id);
    return this.getOne(queryWrapper);

  }

  public String deleteImpeach(Long id) {

    String errorCode;
    String errorMessage;

    Boolean result = this.removeById(id);
    if (result) {
      return "删除成功！";
    } else {
      errorCode = "2003";
      errorMessage = errorCodeMap.getErrorMessage(errorCode);
      throw new CustomException(errorMessage);
    }
  }
}
