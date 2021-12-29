package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("org_role")
@Data
public class OrgRoleKey {
    private Integer orgId;

    private Integer roleId;

}