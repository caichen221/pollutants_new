package com.iscas.biz.security.service;

import com.iscas.biz.security.domain.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}
 
 
