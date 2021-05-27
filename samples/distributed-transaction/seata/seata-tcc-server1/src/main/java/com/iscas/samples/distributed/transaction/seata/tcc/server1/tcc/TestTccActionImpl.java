package com.iscas.samples.distributed.transaction.seata.tcc.server1.tcc;

import com.alibaba.fastjson.JSONObject;
import com.iscas.samples.distributed.transaction.seata.tcc.server1.mapper.UserMapper;
import com.iscas.samples.distributed.transaction.seata.tcc.server1.po.User;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 11:00
 * @since jdk1.8
 */
@Component
@Slf4j
public class TestTccActionImpl implements TestTccAction {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public boolean prepareTest(BusinessActionContext businessActionContext, User user) {
        log.info("创建 test 第一阶段，预留资源 - " + businessActionContext.getXid());

        userMapper.insert(user);
        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), businessActionContext.getXid(), "p");
        return true;
    }

    @Transactional
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        log.info("创建 test 第二阶段提交，修改用户名为zhangsan1 - " + businessActionContext.getXid());

        // 防止幂等性，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }

        JSONObject jsonObject = (JSONObject) businessActionContext.getActionContext().get("user");
        User user = jsonObject.toJavaObject(User.class);
        user.setName("zhangsan1");
        userMapper.update(user);

        //提交成功时删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        log.info("创建 test 第二阶段回滚，删除用户 - " + businessActionContext.getXid());

        //第一阶段没有完成的情况下，不必执行回滚
        //因为第一阶段有本地事务，事务失败时已经进行了回滚。
        //如果这里第一阶段成功，而其他全局事务参与者失败，这里会执行回滚
        //幂等性控制：如果重复执行回滚则直接返回
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }

        JSONObject jsonObject = (JSONObject) businessActionContext.getActionContext().get("user");
        User user = jsonObject.toJavaObject(User.class);
        if (user != null) {
            userMapper.delete(user);
        }

        //回滚结束时，删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }
}
