package com.iscas.biz.service.common;

import com.iscas.biz.domain.common.Role;
import com.iscas.biz.mapper.common.RoleMapper;
import com.iscas.common.tools.exception.lambda.LambdaExceptionUtils;
import com.iscas.templet.view.table.ComboboxData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色service
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/21 19:50
 * @since jdk1.8
 */
@Service
public class RoleService {
    private final RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<ComboboxData> combobox() {
        List<Role> roles = roleMapper.selectByExample(null);
        if (CollectionUtils.isNotEmpty(roles)) {
            return roles.stream().map(LambdaExceptionUtils.lambdaWrapper(role -> {
                ComboboxData comboboxData = new ComboboxData();
                comboboxData.setLabel(role.getRoleName())
                        .setValue(role.getRoleId())
                        .setData(role);
                return comboboxData;
            })).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
