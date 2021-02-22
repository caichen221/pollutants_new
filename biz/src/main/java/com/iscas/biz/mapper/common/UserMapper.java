package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.User;
import com.iscas.biz.domain.common.UserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select t1.user_id, t2.role_id, t3.role_name from user t1, user_role t2, role t3 where t1.user_id = t2.user_id and t2.role_id = t3.role_id")
    List<Map> selectUserRole();

    @Select("select t1.user_id, t2.org_id, t3.org_name from user t1, org_user t2, org t3 where t1.user_id = t2.user_id and t2.org_id = t3.org_id")
    List<Map> selectOrgUser();

}