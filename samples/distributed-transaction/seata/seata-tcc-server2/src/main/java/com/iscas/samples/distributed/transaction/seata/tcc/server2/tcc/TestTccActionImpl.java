package com.iscas.samples.distributed.transaction.seata.tcc.server2.tcc;

import com.alibaba.fastjson.JSONObject;
import com.iscas.samples.distributed.transaction.seata.tcc.server2.mapper.UserDetailsMapper;
import com.iscas.samples.distributed.transaction.seata.tcc.server2.po.UserDetails;
import io.netty.util.internal.ThreadLocalRandom;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 13:44
 * @since jdk1.8
 */
@Component
@Slf4j
public class TestTccActionImpl implements TestTccAction {
    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean prepareTest(BusinessActionContext businessActionContext, UserDetails userDetails) {
        log.info("测试，第一阶段");
        //防悬挂处理
        if (ResultHolder.getNullRollbackedResult(getClass(), businessActionContext.getXid()) != null) {
            return false;
        }
        //模拟回滚
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException("出错啦");
        }

        //保存标识
        ResultHolder.setResult(getClass(), businessActionContext.getXid(), "p");
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {

        log.info("第二阶段提交");

        //防止重复提交
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }

        JSONObject jsonObject = (JSONObject) businessActionContext.getActionContext().get("userDetails");
        UserDetails userDetails = jsonObject.toJavaObject(UserDetails.class);
        userDetailsMapper.insert(userDetails);

        //删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {

        log.info("第二阶段，回滚");

        //防止空回滚
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            //防悬挂处理
            ResultHolder.setNullRollbackedResult(getClass(), businessActionContext.getXid(), new Date());
            return true;
        }
        JSONObject jsonObject = (JSONObject) businessActionContext.getActionContext().get("userDetails");
        UserDetails userDetails = jsonObject.toJavaObject(UserDetails.class);
        userDetailsMapper.delete(userDetails);

        //删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }
}
