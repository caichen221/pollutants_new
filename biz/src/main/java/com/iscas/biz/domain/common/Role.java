package com.iscas.biz.domain.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Role {
    private Integer roleId;

    private String roleName;

    private Date roleCreateTime;

    private Date roleUpdateTime;

}