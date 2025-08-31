package com.wangashu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.User;

public interface LoginService extends IService<User> {
    ResponseResult login(User user);
}
