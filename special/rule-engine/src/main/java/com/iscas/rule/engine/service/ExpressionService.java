package com.iscas.rule.engine.service;

import com.iscas.common.tools.assertion.AssertCollectionUtils;
import com.iscas.common.tools.assertion.AssertObjUtils;
import com.iscas.rule.engine.model.Expression;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 表达式处理业务
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/11/24 15:36
 * @since jdk1.8
 */
public abstract class ExpressionService {
    /**
     * 观测参数对应的表达式
     * */
    private ConcurrentHashMap<String, Collection<Expression>> PARAM_EXPRESSIONS = new ConcurrentHashMap<>();
//    private ConcurrentHashMap<String, Collection<String>> EXPRESION_PARAMS = new ConcurrentHashMap<>();

    /**
     * 添加规则表达式
     * */
    protected List<Boolean> addExpression(List<Expression> expressions) {
        AssertCollectionUtils.assertCollectionNotEmpty(expressions, "规则表达式不能为空");
        return expressions.stream().map(expression -> addExpression(expression)).collect(Collectors.toList());
    }

    /**
     * 添加规则表达式
     * */
    protected boolean addExpression(Expression expression) {
        AssertObjUtils.assertNotNull(expression, "规则表达式不能为空");
        LinkedHashMap<String, String> params = expression.getParams();
        for (String value : params.values()) {
            Collection<Expression> expressions = PARAM_EXPRESSIONS.get(value);
            if (CollectionUtils.isEmpty(expressions)) {
                expressions = new HashSet<>();
                PARAM_EXPRESSIONS.put(value, expressions);
            }
            expressions.add(expression);
        }

//        EXPRESION_PARAMS.put(expression.getRegulation().getId(), getParamsOfExpression(expression));
        return true;
    }

    /**
     * 删除规则表达式
     * */
    protected boolean removeExpression(Expression expression) {
        AssertObjUtils.assertNotNull(expression, "规则表达式不能为空");

//        for (String id : EXPRESION_PARAMS.keySet()) {
//            if (Objects.equals(id, expression.getRegulation().getId())) {
//                EXPRESION_PARAMS.remove(id);
//            }
//        }
        for (Map.Entry<String, Collection<Expression>> entry : PARAM_EXPRESSIONS.entrySet()) {
            Iterator<Expression> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                Expression next = iterator.next();
                if (Objects.equals(next.getRegulation().getId(), expression.getRegulation().getId())) {
                    iterator.remove();
                }
            }
        }
        return true;
    }

    /**
     * 获取某个参数对应的表达式们
     * */
    protected Collection<Expression> getExpressionByKey(String key) {
        return PARAM_EXPRESSIONS.get(key);
    }

//    /**
//     * 获取某一个表达式使用的参数
//     * */
//    protected Collection<String> getParamsOfExpression(Expression expression) {
//        LinkedHashMap<String, String> params = expression.getParams();
//        return params.values();
//    }


    /**
     * 读取规则引擎，建议从数据库读取，再调用addExpression方法
     * */
    protected abstract void readExpression();

}
