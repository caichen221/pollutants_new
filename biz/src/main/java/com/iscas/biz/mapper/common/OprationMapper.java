package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.domain.common.OprationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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