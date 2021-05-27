package com.iscas.samples.distributed.transaction.seata.tcc.server1.tcc;

import com.iscas.samples.distributed.transaction.seata.tcc.server1.po.User;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 10:53
 * @since jdk1.8
 */
@LocalTCC
public interface TestTccAction {

    /**
    第一阶段的方法
    通过注解指定第二阶段的两个方法名

    BusinessActionContext 上下文对象，用来在两个阶段之间传递数据
    @BusinessActionContextParameter 注解的参数数据会被存入 BusinessActionContext
     */
    @TwoPhaseBusinessAction(name = "testTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepareTest(BusinessActionContext businessActionContext,
                        @BusinessActionContextParameter(paramName = "user") User user);

    // 第二阶段 - 提交
    boolean commit(BusinessActionContext businessActionContext);

    // 第二阶段 - 回滚
    boolean rollback(BusinessActionContext businessActionContext);

}
