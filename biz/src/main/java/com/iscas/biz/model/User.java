package com.iscas.biz.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Configuration(proxyBeanMethods = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID(暂时用自增主键)
     */
//    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     * */
    private String role;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 所属单位(或组织)
     */
    private String unit;

}
