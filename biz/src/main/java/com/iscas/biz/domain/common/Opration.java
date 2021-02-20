package com.iscas.biz.domain.common;

import java.util.Date;

public class Opration {
    private Integer opId;

    private String opName;

    private Date opCreateTime;

    private Date opUpdateTime;

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName == null ? null : opName.trim();
    }

    public Date getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(Date opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public Date getOpUpdateTime() {
        return opUpdateTime;
    }

    public void setOpUpdateTime(Date opUpdateTime) {
        this.opUpdateTime = opUpdateTime;
    }
}