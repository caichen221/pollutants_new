package com.iscas.biz.mp.enhancer.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
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
@ConditionalOnMybatis
public interface DynamicMapper<T> extends BaseMapper<T> {

    /**
     * 扩展Mybatis-Plus的方法
     * truncate
     * */
    void truncate();

    /**
     * 扩展Mybatis-Plus的方法
     * fetchByStream
     * */
    void fetchByStream(@Param(Constants.WRAPPER) Wrapper<T> wrapper, ResultHandler<T> handler);

    @Select("${sql}" )
    Map selectOneBySql(@Param("sql") String sql);

    @Select("${sql}" )
    List<Map> selectBySql(@Param("sql") String sql);

    @Insert("${sql}")
    void insertBySql(@Param("sql") String sql);

    @Update("${sql}")
    void updateBySql(@Param("sql") String sql);

    @Delete("${sql}")
    void deleteBySql(@Param("sql") String sql);

    @Select("${sql}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(Map.class)
    void selectLargeDataBySql(@Param("sql") String sql, ResultHandler<Map> handler);

    @Update("<script><foreach close=\"\" collection=\"sqls\" index=\"index\" item=\"item\" open=\"\" separator=\";\">  " +
            "     ${item}       " +
            "        </foreach></script>  ")
    void batchBySql(@Param("sqls") List<String> sqls);


//    /**
//     * 废弃，{@link #selectOne(String)}
//     * */
//    @Select("${sql}" )
//    @Deprecated
//    Map dynamicSelectOne(@Param("sql") String sql);
//
//    /**
//     * 废弃，{@link #select(String)}
//     * */
//    @Select("${sql}" )
//    @Deprecated
//    List<Map> dynamicSelect(@Param("sql") String sql);
//
//    /**
//     * 废弃，{@link #insert(String)}
//     * */
//    @Insert("${sql}")
//    @Deprecated
//    void dynamicInsert(@Param("sql") String sql);
//
//    /**
//     * 废弃，{@link #update(String)}
//     * */
//    @Update("${sql}")
//    @Deprecated
//    void dynamicUpdate(@Param("sql") String sql);
//
//    /**
//     * 废弃，{@link #delete(String)}
//     * */
//    @Delete("${sql}")
//    @Deprecated
//    void dynamicDelete(@Param("sql") String sql);
//
//    /**
//     * 废弃，{@link #selectLargeData(String, ResultHandler)}
//     * */
//    @Select("${sql}")
//    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
//    @ResultType(Map.class)
//    @Deprecated
//    void dynamicSelectLargeData(@Param("sql") String sql, ResultHandler<Map> handler);
//
//    /**
//     * 废弃，{@link #batch(List)}
//     * */
//    @Update("<script><foreach close=\"\" collection=\"sqls\" index=\"index\" item=\"item\" open=\"\" separator=\";\">  " +
//            "     ${item}       " +
//            "        </foreach></script>  ")
//    @Deprecated
//    void dynamicBatch(@Param("sqls") List<String> sqls);


}
