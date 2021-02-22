package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.OrgUserExample;
import com.iscas.biz.domain.common.OrgUserKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgUserMapper {
    long countByExample(OrgUserExample example);

    int deleteByExample(OrgUserExample example);

    int deleteByPrimaryKey(OrgUserKey key);

    int insert(OrgUserKey record);

    int insertSelective(OrgUserKey record);

    List<OrgUserKey> selectByExample(OrgUserExample example);

    int updateByExampleSelective(@Param("record") OrgUserKey record, @Param("example") OrgUserExample example);

    int updateByExample(@Param("record") OrgUserKey record, @Param("example") OrgUserExample example);
}