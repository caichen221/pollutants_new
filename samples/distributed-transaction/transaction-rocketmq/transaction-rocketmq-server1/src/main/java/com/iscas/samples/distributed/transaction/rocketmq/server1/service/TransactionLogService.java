package com.iscas.samples.distributed.transaction.rocketmq.server1.service;

import com.iscas.samples.distributed.transaction.rocketmq.server1.mapper.TransactionLogMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server1.model.TransactionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 10:47
 * @since jdk1.8
 */
@Service
public class TransactionLogService {
    @Autowired
    private TransactionLogMapper transactionLogMapper;
    public Integer getCountById(String id) {
        return transactionLogMapper.selectCountById(id);
    }
}
