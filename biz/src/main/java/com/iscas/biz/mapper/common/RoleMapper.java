package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.domain.common.Role;
import com.iscas.biz.domain.common.RoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    @Select("select DISTINCT t1.role_id, t7.resource_id from role t1, role_menu t2, menu t3, menu_opration t4, opration t5, opration_resource t6, resource t7 \n" +
            "\twhere t1.role_id = t2.role_id and t2.menu_id = t3.menu_id and t3.menu_id = t4.menu_id\n" +
            "  and t4.op_id = t5.op_id and t5.op_id = t6.op_id and t6.resource_id = t7.resource_id order by t1.role_id")
    List<Map> selectRoleResource();


    @Select("select t2.* from role_opration t1, opration t2 where t1.op_id = t2.op_id and t1.role_id = #{roleId}")
    List<Opration> selectOprationByRoleId(@Param("roleId") Integer roleId);
}