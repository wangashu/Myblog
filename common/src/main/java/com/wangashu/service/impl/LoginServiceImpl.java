package com.wangashu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.LoginUser;
import com.wangashu.entity.User;

import com.wangashu.mapper.UserMapper;
import com.wangashu.service.LoginService;
import com.wangashu.utils.BeanCopyUtils;
import com.wangashu.utils.JwtUtil;
import com.wangashu.utils.RedisCache;
import com.wangashu.vo.UserInfoVo;
import com.wangashu.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if (Objects.isNull(authenticate)){
            throw  new RuntimeException("用户或者密码错误");
        }
        //获取用户id 生成jwt
         LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存到redis中去
       redisCache.setCacheObject("bloglogin"+userId,loginUser);

      //吧token好userInfo 返回

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(userLoginVo);
    }
}
