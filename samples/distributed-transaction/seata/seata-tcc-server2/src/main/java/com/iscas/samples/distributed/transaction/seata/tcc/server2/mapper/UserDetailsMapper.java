package com.iscas.samples.distributed.transaction.seata.tcc.server2.mapper;


import com.iscas.samples.distributed.transaction.seata.tcc.server2.po.UserDetails;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 22:24
 * @since jdk1.8
 */
public interface UserDetailsMapper {
    int insert(UserDetails userDetails);
    int delete(UserDetails userDetails);
}
