package com.iscas.templet.view.validator;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2018/1/23 10:22
 * @Modified:
 **/
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@Builder
public class Rule implements Serializable {
    protected boolean required = false; //是否必须填写值
    protected String reg; //正则表达式
    protected Map<String,Integer> length; //长度  min,max
    protected boolean distinct = false; //是否要校验重复值
    protected boolean containsHigh = false; //是否包含最大值
    protected boolean containsLow = false; //是否包含最小值
    protected Object highVal;
    protected Object lowVal;
    protected String desc = null; //提示描述
}
