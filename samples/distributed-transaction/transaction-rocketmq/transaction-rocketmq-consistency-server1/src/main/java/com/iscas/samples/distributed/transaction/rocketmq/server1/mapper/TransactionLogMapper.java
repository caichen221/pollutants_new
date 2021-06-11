package com.iscas.samples.distributed.transaction.rocketmq.server1.mapper;

import com.iscas.samples.distributed.transaction.rocketmq.server1.model.TransactionLog;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:36
 * @since jdk1.8
 */
public interface TransactionLogMapper {
    int insert(TransactionLog transactionLog);
    int selectCountById(@Param("id") String id);
}
