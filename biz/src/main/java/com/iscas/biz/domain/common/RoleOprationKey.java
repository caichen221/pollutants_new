package com.iscas.biz.domain.common;

public class RoleOprationKey {
    private Integer roleId;

    private Integer opId;

    public RoleOprationKey() {}

    public RoleOprationKey(Integer roleId, Integer opId) {
        this.roleId = roleId;
        this.opId = opId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }
}
