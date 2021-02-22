package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.MenuOprationExample;
import com.iscas.biz.domain.common.MenuOprationKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOprationMapper {
    long countByExample(MenuOprationExample example);

    int deleteByExample(MenuOprationExample example);

    int deleteByPrimaryKey(MenuOprationKey key);

    int insert(MenuOprationKey record);

    int insertSelective(MenuOprationKey record);

    List<MenuOprationKey> selectByExample(MenuOprationExample example);

    int updateByExampleSelective(@Param("record") MenuOprationKey record, @Param("example") MenuOprationExample example);

    int updateByExample(@Param("record") MenuOprationKey record, @Param("example") MenuOprationExample example);
}