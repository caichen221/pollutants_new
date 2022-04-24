package com.iscas.rule.engine.service;

import com.iscas.rule.engine.model.DataBean;

/**
 * 队列service接口
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/11/24 16:27
 * @since jdk1.8
 */
public interface IQueueService {
    boolean offer(DataBean dataBean);
    DataBean pull();
    void initQueues();
}
