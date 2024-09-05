package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.potato.common.ErrorCodeMap;
import com.potato.controller.dto.ListInfo;
import com.potato.entity.ImpeachInfo;
import com.potato.mapper.ImpeachInfoMapper;
import com.potato.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

  private final ErrorCodeMap errorCodeMap = new ErrorCodeMap();

  @Autowired
  private ImpeachInfoMapper impeachInfoMapper;

  @Override
  public ListInfo getAllImpeach(int pageNum) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    int total = impeachInfoMapper.selectList(queryWrapper).size();

    Page<ImpeachInfo> page = new Page<>(pageNum, 2); // 第1页，每页2条记录
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page, queryWrapper);
    List<ImpeachInfo> infoList = resultPage.getRecords();

    ListInfo result = new ListInfo();
    result.setTotal(total);
    result.setLists(infoList);

    return result;
  }

  @Override
  public ListInfo getUnTreatedImpeach(int pageNum) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getProcessId,1);
    int total = impeachInfoMapper.selectList(queryWrapper).size();

    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    Page<ImpeachInfo> page = new Page<>(pageNum, 2);
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page, queryWrapper);
    List<ImpeachInfo> infoList = resultPage.getRecords();

    ListInfo result = new ListInfo();
    result.setTotal(total);
    result.setLists(infoList);

    return result;
  }

  @Override
  public ListInfo getTreatedImpeach(int pageNum) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(ImpeachInfo::getProcessId,0,2);
    int total = impeachInfoMapper.selectList(queryWrapper).size();

    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    Page<ImpeachInfo> page = new Page<>(pageNum, 2);
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page, queryWrapper);
    List<ImpeachInfo> infoList = resultPage.getRecords();

    ListInfo result = new ListInfo();
    result.setTotal(total);
    result.setLists(infoList);

    return result;
  }

  @Override
  public String passImpeach(Long id) {
    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getId,id);
    ImpeachInfo info = impeachInfoMapper.selectOne(queryWrapper);
    info.setProcessId(2);
    impeachInfoMapper.updateById(info);
    return "通过举报成功！";
  }

  @Override
  public String vetoImpeach(Long id) {
    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getId,id);
    ImpeachInfo info = impeachInfoMapper.selectOne(queryWrapper);
    info.setProcessId(0);
    impeachInfoMapper.updateById(info);
    return "否决举报成功！";
  }
}
