package com.iscas.biz.flowable.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作流任务
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:24
 * @since jdk11
 */
@Data
@Schema(title = "工作流任务相关-返回参数")
public class FlowTaskDto implements Serializable {
    @Schema(title = "任务编号")
    private String taskId;

    @Schema(title = "任务名称")
    private String taskName;

    @Schema(title = "任务Key")
    private String taskDefKey;

    @Schema(title = "任务执行人Id")
    private Long assigneeId;

    @Schema(title = "部门名称")
    private String deptName;

    @Schema(title = "流程发起人部门名称")
    private String startDeptName;

    @Schema(title = "任务执行人名称")
    private String assigneeName;

    @Schema(title = "流程发起人Id")
    private String startUserId;

    @Schema(title = "流程发起人名称")
    private String startUserName;

    @Schema(title = "流程类型")
    private String category;

    @Schema(title = "流程变量信息")
    private Object procVars;

    @Schema(title = "局部变量信息")
    private Object taskLocalVars;

    @Schema(title = "流程部署编号")
    private String deployId;

    @Schema(title = "流程ID")
    private String procDefId;

    @Schema(title = "流程key")
    private String procDefKey;

    @Schema(title = "流程定义名称")
    private String procDefName;

    @Schema(title = "流程定义内置使用版本")
    private int procDefVersion;

    @Schema(title = "流程实例ID")
    private String procInsId;

    @Schema(title = "历史流程实例ID")
    private String hisProcInsId;

    @Schema(title = "任务耗时")
    private String duration;

    @Schema(title = "任务意见")
    private FlowCommentDto comment;

    @Schema(title = "候选执行人")
    private String candidate;

    @Schema(title = "任务创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(title = "任务完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
}
