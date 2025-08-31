package com.wangashu.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangashu.entity.LoginUser;
import com.wangashu.entity.User;
import com.wangashu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class UserDetailServiceImpl implements UserDetailsService {

     @Autowired
     private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //更具条件查询
        LambdaQueryWrapper<com.wangashu.entity.User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username);
        com.wangashu.entity.User user = userMapper.selectOne(wrapper);

        //判断用户不存在直接抛异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        return new LoginUser(user);
    }
}
