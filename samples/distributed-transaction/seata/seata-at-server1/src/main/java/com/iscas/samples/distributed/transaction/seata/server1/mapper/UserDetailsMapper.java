package com.iscas.samples.distributed.transaction.seata.server1.mapper;

import com.iscas.samples.distributed.transaction.seata.server1.po.UserDetails;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:24
 * @since jdk1.8
 */
public interface UserDetailsMapper {
    int insert(UserDetails userDetails);
}
