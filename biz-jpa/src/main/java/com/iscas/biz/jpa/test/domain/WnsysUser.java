package com.iscas.biz.jpa.test.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * User: xts
 * Date: 2017-11-14
 * Time: 11:01
 * Description:
 */
@MappedSuperclass
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WnsysUser implements Serializable {
    @Column(length = 16)
    protected String nickName;
    @Column
    protected String orgName;
    @Transient
    protected String orgId;

    @Transient
    protected List<Role> roles;

    @Transient
    protected Integer role;

    @Column(length = 1)
    protected String status;
    @Column(length = 11)
    protected String tel;
    @Column(length = 50)
    protected String email;
    @Column(length = 1)
    protected String share; //是否能够将登录状态分享给他人，1 是 2 否

}
