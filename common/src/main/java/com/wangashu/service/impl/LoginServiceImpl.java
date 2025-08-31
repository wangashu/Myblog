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
import org.springframework.security.core.AuthenticationException;
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

            //封装登录的用户名和密码
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
            //在下一行之前，封装的数据会先走UserDetailsServiceImpl实现类，这个实现类在我们的huanf-framework工程的service/impl目录里面
          /*  Authentication authenticate = authenticationManager.authenticate(authenticationToken);*/
            //上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            // 认证失败直接捕获异常
            throw new RuntimeException("用户名或密码错误", e);
        }

        //获取userid
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            String userId = loginUser.getUser().getId().toString();
            //把这个userid通过我们写的JwtUtil工具类转成密文，这个密文就是token值
            String jwt = JwtUtil.createJWT(userId);

            //下面那行的第一个参数: 把上面那行的jwt，也就是token值保存到Redis。存到时候是键值对的形式，值就是jwt，key要加上 "bloglogin:" 前缀
            //下面那行的第二个参数: 要把哪个对象存入Redis。我们写的是loginUser，里面有权限信息，后面会用到
            redisCache.setCacheObject("bloglogin:"+userId,loginUser);


            //把User转化为UserInfoVo，再放入vo对象的第二个参数
            UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
            UserLoginVo vo = new UserLoginVo(jwt,userInfoVo);
            //封装响应返回
            return ResponseResult.okResult(vo);
    }
}
