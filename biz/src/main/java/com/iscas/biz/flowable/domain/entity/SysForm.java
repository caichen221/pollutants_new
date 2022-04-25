package com.iscas.biz.flowable.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:57
 * @since jdk11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysForm extends BaseEntity {
    /**
     * 表单主键
     */
    private Long formId;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单内容
     */
    private String formContent;
}
