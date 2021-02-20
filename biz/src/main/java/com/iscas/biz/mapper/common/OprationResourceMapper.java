package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.OprationResourceExample;
import com.iscas.biz.domain.common.OprationResourceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OprationResourceMapper {
    long countByExample(OprationResourceExample example);

    int deleteByExample(OprationResourceExample example);

    int deleteByPrimaryKey(OprationResourceKey key);

    int insert(OprationResourceKey record);

    int insertSelective(OprationResourceKey record);

    List<OprationResourceKey> selectByExample(OprationResourceExample example);

    int updateByExampleSelective(@Param("record") OprationResourceKey record, @Param("example") OprationResourceExample example);

    int updateByExample(@Param("record") OprationResourceKey record, @Param("example") OprationResourceExample example);
}