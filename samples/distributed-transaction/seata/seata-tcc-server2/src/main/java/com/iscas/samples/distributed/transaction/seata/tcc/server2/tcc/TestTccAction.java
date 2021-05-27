package com.iscas.samples.distributed.transaction.seata.tcc.server2.tcc;

import com.iscas.samples.distributed.transaction.seata.tcc.server2.po.UserDetails;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 13:42
 * @since jdk1.8
 */
@LocalTCC
public interface TestTccAction {

    @TwoPhaseBusinessAction(name = "test2TccACTION", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepareTest(BusinessActionContext businessActionContext,
                                   @BusinessActionContextParameter(paramName = "userDetails") UserDetails userDetails);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
