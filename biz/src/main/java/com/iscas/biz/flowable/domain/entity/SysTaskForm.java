package com.iscas.biz.flowable.domain.entity;

import lombok.Data;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 10:00
 * @since jdk11
 */
@Data
public class SysTaskForm extends BaseEntity{

    /** 主键 */
    private Long id;

    /** 表单主键 */
//    @Excel(name = "表单主键")
    private Long formId;

    /** 所属任务 */
//    @Excel(name = "所属任务")
    private String taskId;
}
