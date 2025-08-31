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
/**
 * @author: wangashu
 * @date: 2025/8/31 16:19
 * @className: UserDetailServiceImpl
 * @description: TODO
 */
     @Autowired
     private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            //根据拿到的用户名，并结合查询条件(数据库是否有这个用户名)，去查询用户信息
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName,username);
            User user = userMapper.selectOne(queryWrapper);

            //判断是否查询到用户，也就是这个用户是否存在，如果没查到就抛出异常
            if(Objects.isNull(user)){
                throw new RuntimeException("用户不存在");//后期会对异常进行统一处理
            }

            //TODO 查询权限信息，并封装

            //返回查询到的用户信息。注意下面那行直接返回user会报错，我们需要在huanf-framework工程的domain目录新
            //建LoginUser类，在LoginUser类实现UserDetails接口，然后下面那行就返回LoginUser对象
            return new LoginUser(user);
    }
}
