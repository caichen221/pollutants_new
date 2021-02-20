package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.OrgRoleExample;
import com.iscas.biz.domain.common.OrgRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgRoleMapper {
    long countByExample(OrgRoleExample example);

    int deleteByExample(OrgRoleExample example);

    int deleteByPrimaryKey(OrgRoleKey key);

    int insert(OrgRoleKey record);

    int insertSelective(OrgRoleKey record);

    List<OrgRoleKey> selectByExample(OrgRoleExample example);

    int updateByExampleSelective(@Param("record") OrgRoleKey record, @Param("example") OrgRoleExample example);

    int updateByExample(@Param("record") OrgRoleKey record, @Param("example") OrgRoleExample example);
}