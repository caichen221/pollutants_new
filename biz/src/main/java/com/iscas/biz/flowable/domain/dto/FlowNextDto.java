package com.iscas.biz.flowable.domain.dto;

import com.iscas.biz.domain.common.Role;
import com.iscas.biz.domain.common.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 动态人员、组
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:21
 * @since jdk11
 */
@Data
public class FlowNextDto implements Serializable {
    private String type;

    private String vars;

    private List<User> userList;

    private List<Role> roleList;
}
