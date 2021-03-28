package com.iscas.sso.oauth2.code.uaa.service;


import com.iscas.sso.oauth2.code.uaa.domain.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}
 
 
