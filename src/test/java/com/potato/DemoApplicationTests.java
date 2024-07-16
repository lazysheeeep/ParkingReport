package com.potato;

import com.potato.entity.User;
import com.potato.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        System.out.println(userMapper);
        //参数是一个Wrapper，条件结构器，这里先不用 填null
        //查询所有的用户
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(1,userList.size());
        userList.forEach(System.out::println);
    }

}
