package com.iscas.samples.distributed.transaction.seata.at.server1.mapper;

import com.iscas.samples.distributed.transaction.seata.at.server1.po.User;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:24
 * @since jdk1.8
 */
public interface UserMapper {
    int insert(User user);
}
