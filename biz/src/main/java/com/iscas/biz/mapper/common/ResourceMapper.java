package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Resource;
import com.iscas.biz.domain.common.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceMapper {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(Integer resourceId);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExampleWithBLOBs(ResourceExample example);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(Integer resourceId);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExampleWithBLOBs(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKeyWithBLOBs(Resource record);

    int updateByPrimaryKey(Resource record);
}