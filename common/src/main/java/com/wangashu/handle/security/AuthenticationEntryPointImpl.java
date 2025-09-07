package com.wangashu.handle.security;

import com.alibaba.fastjson.JSON;
import com.wangashu.domain.ResponseResult;
import com.wangashu.enums.AppHttpCodeEnum;
import com.wangashu.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author: wangashu
 * @date: 2025/9/7 15:19
 * @className: AuthenticationEntryPointImpl
 */
@Component
public class AuthenticationEntryPointImpl  implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        //认证失败异常处理 认证失败（没登录） → 由 AuthenticationEntryPoint 处理（比如跳转到登录页）。
        ResponseResult result=null;
        if (e instanceof InsufficientAuthenticationException){
            //没有携带token
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else if (e instanceof BadCredentialsException) {

            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else {
            //第一个参数返回的是响应码，AppHttpCodeEnum是我们写的实体类。第二个参数是返回具体的信息
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));


    }
}
