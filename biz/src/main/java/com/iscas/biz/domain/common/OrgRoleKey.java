package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhuquanwen
 */
@TableName("org_role")
@Data
public class OrgRoleKey {
    private Integer orgId;

    private Integer roleId;

}