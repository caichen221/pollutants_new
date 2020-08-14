package com.iscas.biz.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 执行SQL语句的Mapper
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/9/27 10:23
 * @since jdk1.8
 */
@Repository
public interface DynamicMapper extends BaseMapper {

    @Select("${sql}" )
    Map dynamicSelectOne(@Param("sql") String sql);

    @Select("${sql}" )
    List<Map> dynamicSelect(@Param("sql") String sql);

    @Insert("${sql}")
    void dynamicInsert(@Param("sql") String sql);

    @Update("${sql}")
    void dynamicUpdate(@Param("sql") String sql);

    @Delete("${sql}")
    void dynamicDelete(@Param("sql") String sql);

    @Select("${sql}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(Map.class)
    void dynamicSelectLargeData(@Param("sql") String sql, ResultHandler<Map> handler);

    @Update("<script><foreach close=\"\" collection=\"sqls\" index=\"index\" item=\"item\" open=\"\" separator=\";\">  " +
            "     ${item}       " +
            "        </foreach></script>  ")
    void dynamicBatch(@Param("sqls") List<String> sqls);


}
