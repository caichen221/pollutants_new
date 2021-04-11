//package com.iscas.sso.oauth2.oauthcode.resource.demo.domain;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface SysUserRepository extends CrudRepository<SysUser,Integer> {
//
//    @Query("select a from SysUser a where a.name=:name")
//    public SysUser getUserByName(@Param("name") String name);
//}