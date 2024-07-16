package com.potato.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.potato.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.stream.BaseStream;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
