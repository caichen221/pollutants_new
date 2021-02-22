package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.domain.common.OprationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OprationMapper {
    long countByExample(OprationExample example);

    int deleteByExample(OprationExample example);

    int deleteByPrimaryKey(Integer opId);

    int insert(Opration record);

    int insertSelective(Opration record);

    List<Opration> selectByExample(OprationExample example);

    Opration selectByPrimaryKey(Integer opId);

    int updateByExampleSelective(@Param("record") Opration record, @Param("example") OprationExample example);

    int updateByExample(@Param("record") Opration record, @Param("example") OprationExample example);

    int updateByPrimaryKeySelective(Opration record);

    int updateByPrimaryKey(Opration record);
}