package com.iscas.biz.filter;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.filter.started.AbstractStartedFilter;
import com.iscas.base.biz.filter.started.StartedFilterComponent;
import com.iscas.biz.domain.common.*;
import com.iscas.biz.mapper.common.RoleMapper;
import com.iscas.biz.mapper.common.UserMapper;
import com.iscas.biz.mapper.common.UserRoleMapper;
import com.iscas.common.tools.core.security.MD5Utils;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/21 13:50
 * @since jdk1.8
 */
@StartedFilterComponent(order = 1)
@Slf4j
public class InitUserRoleFilter extends AbstractStartedFilter {

    @Override
    public void doFilterInternal(ApplicationContext applicationContext) {
        try {
            initUserRole(applicationContext);
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
        super.doFilterInternal(applicationContext);
    }

    @Override
    public String getName() {
        return "初始化用户角色过滤器";
    }

    private void initUserRole(ApplicationContext applicationContext) throws BaseException, NoSuchAlgorithmException {
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        RoleMapper roleMapper = applicationContext.getBean(RoleMapper.class);
        UserRoleMapper userRoleMapper = applicationContext.getBean(UserRoleMapper.class);
        Environment environment = applicationContext.getBean(Environment.class);
        String superUserDefaultPwd = environment.getProperty("super_user_default_pwd");
        if (StringUtils.isEmpty(superUserDefaultPwd)) {
            throw new BaseException("超级管理员密码未配置");
        }
        User user = userMapper.selectByUserName(Constants.SUPER_USER_KEY);
        if (user == null) {
            user = new User();
            user.setUserName(Constants.SUPER_USER_KEY)
                    .setUserPwd(MD5Utils.saltMD5(superUserDefaultPwd));
            userMapper.insertUser(user);
        }
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(Constants.SUPER_ROLE_KEY);
        Role role = null;
        List<Role> superRoles = roleMapper.selectByExample(roleExample);
        if (CollectionUtils.isNotEmpty(superRoles)) {
            role = superRoles.get(0);
        } else {
            role = new Role();
            role.setRoleName(Constants.SUPER_ROLE_KEY);
            roleMapper.insert(role);
        }
        //超级管理员和超级管理员角色关联
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(user.getUserId())
                .andRoleIdEqualTo(role.getRoleId());
        userRoleMapper.deleteByExample(userRoleExample);
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setRoleId(role.getRoleId());
        userRoleKey.setUserId(user.getUserId());
        userRoleMapper.insert(userRoleKey);
    }
}
