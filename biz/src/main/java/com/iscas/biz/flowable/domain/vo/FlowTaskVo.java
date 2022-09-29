package com.iscas.biz.flowable.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:26
 * @since jdk11
 */
@Data
@Schema(title = "工作流任务相关--请求参数")
public class FlowTaskVo implements Serializable {
    @Schema(title = "任务Id")
    private String taskId;

    @Schema(title = "用户Id")
    private String userId;

    @Schema(title = "任务意见")
    private String comment;

    @Schema(title = "流程实例Id")
    private String instanceId;

    @Schema(title = "节点")
    private String targetKey;

    @Schema(title = "流程变量信息")
    private Map<String, Object> values;

    @Schema(title = "审批人")
    private String assignee;

    @Schema(title = "候选人")
    private List<String> candidateUsers;

    @Schema(title = "审批组")
    private List<String> candidateGroups;
}
