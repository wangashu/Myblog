package com.wangashu.vo;

import com.wangashu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {

  private  String token;

  private  UserInfoVo  userInfo;

}
