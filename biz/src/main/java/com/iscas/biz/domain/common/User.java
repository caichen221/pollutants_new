package com.iscas.biz.domain.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
   private Integer userId;
   private String userName;
   private String userRealName;
   private String userPwd;
   private String userTel;
   private String userEmail;
   private Integer userStatus;
   private Date userCreateTime;
   private Date userUpdateTime;
}