package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.Menu;
import com.iscas.biz.domain.common.MenuExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    @Select("select t1.menu_id, t2.op_id, t3.op_name from menu t1, menu_opration t2, opration t3 where t1.menu_id = t2.menu_id and t2.op_id = t3.op_id")
    List<Map> selectMenuOpration();
}