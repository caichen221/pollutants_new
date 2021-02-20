package com.iscas.biz.domain.common;

import java.util.Date;

public class Org {
    private Integer orgId;

    private String orgName;

    private Integer orgPid;

    private Date orgCreateTime;

    private Date orgUpdateTime;

    private String orgDesc;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Integer getOrgPid() {
        return orgPid;
    }

    public void setOrgPid(Integer orgPid) {
        this.orgPid = orgPid;
    }

    public Date getOrgCreateTime() {
        return orgCreateTime;
    }

    public void setOrgCreateTime(Date orgCreateTime) {
        this.orgCreateTime = orgCreateTime;
    }

    public Date getOrgUpdateTime() {
        return orgUpdateTime;
    }

    public void setOrgUpdateTime(Date orgUpdateTime) {
        this.orgUpdateTime = orgUpdateTime;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc == null ? null : orgDesc.trim();
    }
}