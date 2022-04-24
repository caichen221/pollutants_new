package com.iscas.rule.engine.model;

import lombok.Data;

import java.util.List;

/**
 * 多变量
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2020/1/19 14:44
 * @since jdk1.8
 */
@Data
public class MultiVariable {
    /**参数代号*/
    private String paramCode;

    /**是否取绝对值*/
    private boolean isAbsolute;

    /**计算条件*/
    private List<ComputeCondition> compute;

    /**条件计算*/
    private List<VariableCondition> variableConditions;

    /**逻辑运算符，默认null*/
    private String logicCode = null;


}
