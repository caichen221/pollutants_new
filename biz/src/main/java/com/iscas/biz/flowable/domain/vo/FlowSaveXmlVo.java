package com.iscas.biz.flowable.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:23
 * @since jdk11
 */
@Data
public class FlowSaveXmlVo implements Serializable {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * xml 文件
     */
    private String xml;
}
