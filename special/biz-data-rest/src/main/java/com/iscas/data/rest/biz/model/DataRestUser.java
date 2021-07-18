package com.iscas.data.rest.biz.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/17 14:05
 * @since jdk1.8
 */
@Entity(name = "user_info")
@Data
public class DataRestUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
