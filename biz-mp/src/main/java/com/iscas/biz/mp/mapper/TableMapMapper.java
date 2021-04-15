package com.iscas.biz.mp.mapper;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.model.DynamicSql;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2018/7/16 14:44
 * @Modified:
 **/
@Repository
@Mapper
@ConditionalOnMybatis
public interface TableMapMapper {
    @Select("${sql}" )
    List<Map> dynamicSelect(DynamicSql dynamicSql);

    @Insert("${sql}")
    int dynamicInsert(DynamicSql dynamicSql);
}
