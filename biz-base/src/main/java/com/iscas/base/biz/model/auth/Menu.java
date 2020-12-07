package com.iscas.base.biz.model.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 18:41
 * @since jdk1.8
 */
@Data
public class Menu implements Serializable {
    private String key;
    private String name;

}
