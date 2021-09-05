package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.domain.common.RoleOprationKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleOprationMapper {
    @Delete("delete from role_opration where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    int insertBatch(@Param("roleOprationKeys") List<RoleOprationKey> roleOprationKeys);
}