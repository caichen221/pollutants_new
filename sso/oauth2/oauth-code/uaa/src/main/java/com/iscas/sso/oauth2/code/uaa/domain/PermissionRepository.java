package com.iscas.sso.oauth2.code.uaa.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission,Integer> {
 
}