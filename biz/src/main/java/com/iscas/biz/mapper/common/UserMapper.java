package com.iscas.biz.mapper.common;

import com.iscas.biz.domain.common.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User selectByPrimaryKey(Integer userId);
    void updatePwd(User user);

    @Select("select t1.user_id, t2.role_id, t3.role_name from user t1, user_role t2, role t3 where t1.user_id = t2.user_id and t2.role_id = t3.role_id")
    List<Map> selectUserRole();

    @Select("select t1.user_id, t2.org_id, t3.org_name from user t1, org_user t2, org t3 where t1.user_id = t2.user_id and t2.org_id = t3.org_id")
    List<Map> selectOrgUser();
}