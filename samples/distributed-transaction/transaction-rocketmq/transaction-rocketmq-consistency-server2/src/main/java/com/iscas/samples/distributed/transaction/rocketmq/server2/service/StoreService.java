package com.iscas.samples.distributed.transaction.rocketmq.server2.service;

import com.iscas.samples.distributed.transaction.rocketmq.server2.mapper.OrderMarkMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server2.mapper.StoreMapper;
import com.iscas.samples.distributed.transaction.rocketmq.server2.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/1 15:06
 * @since jdk1.8
 */
@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private OrderMarkMapper orderMarkMapper;

    @Transactional(rollbackFor = Exception.class)
    public void updateStore(Order order) {
        int count = orderMarkMapper.countByOrderId(order.getId());
        //幂等处理
        if (count <= 0) {
            storeMapper.updateStore(order.getNum(), order.getName());
            orderMarkMapper.insertOrderMark(order.getId());
        }
    }
}
