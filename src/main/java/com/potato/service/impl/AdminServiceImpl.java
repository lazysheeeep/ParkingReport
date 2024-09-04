package com.potato.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.potato.common.ErrorCodeMap;
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
  public List<ImpeachInfo> getAllImpeach(int pageNum) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    Page<ImpeachInfo> page = new Page<>(1, 2); // 第1页，每页2条记录
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page, queryWrapper);

    List<ImpeachInfo> records = resultPage.getRecords();

    return records;
  }

  @Override
  public List<ImpeachInfo> getUnTreatedImpeach(int pageNum) {

    LambdaQueryWrapper<ImpeachInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ImpeachInfo::getProcessId,1);
    queryWrapper.orderByDesc(ImpeachInfo::getCreatedAt);
    Page<ImpeachInfo> page = new Page<>(1, 2);
    IPage<ImpeachInfo> resultPage = impeachInfoMapper.selectPage(page, queryWrapper);

    List<ImpeachInfo> records = resultPage.getRecords();

    return records;
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
}
