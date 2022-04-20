package com.iscas.biz.flowable.domain.entity;

import lombok.Data;

/**
 * 流程实例关联表单对象 sys_instance_form
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:55
 * @since jdk11
 */
@Data
public class SysDeployForm extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 表单主键 */
//    @Excel(name = "表单主键")
    private Long formId;

    /** 流程定义主键 */
//    @Excel(name = "流程定义主键")
    private String deployId;
}
