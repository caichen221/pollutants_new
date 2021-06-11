package com.iscas.samples.distributed.transaction.rocketmq.server2.mapper;


import org.apache.ibatis.annotations.Param;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 9:04
 * @since jdk1.8
 */
public interface StoreMapper {
    int updateStore(@Param("num") int num, @Param("name") String name);
}
