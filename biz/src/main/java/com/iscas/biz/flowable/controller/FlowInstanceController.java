package com.iscas.biz.flowable.controller;

import com.iscas.biz.flowable.condition.ConditionalOnFlowable;
import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.biz.flowable.service.IFlowInstanceService;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工作流流程实例管理
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 17:10
 * @since jdk11
 */
@SuppressWarnings({"rawtypes", "unused"})
@Slf4j
@Tag(name = "工作流流程实例管理-FlowInstanceController")
@RestController
@RequestMapping("/flowable/instance")
@RequiredArgsConstructor
@ConditionalOnFlowable
public class FlowInstanceController {
    private final IFlowInstanceService flowInstanceService;

    @Operation(summary = "根据流程定义id启动流程实例")
    @PostMapping("/startBy/{procDefId}")
    public ResponseEntity startById(@Parameter(name = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                                    @Parameter(name = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowInstanceService.startProcessInstanceById(procDefId, variables);

    }


    @Operation(summary = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public ResponseEntity updateState(@Parameter(name = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @Parameter(name = "流程实例ID", required = true) @RequestParam String instanceId) {
        flowInstanceService.updateState(state,instanceId);
        return new ResponseEntity();
    }

    @Operation(summary = "结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = FlowTaskVo.class)))
    public ResponseEntity stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
        flowInstanceService.stopProcessInstance(flowTaskVo);
        return new ResponseEntity();
    }

    @Operation(summary = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@Parameter(name = "流程实例ID", required = true) @RequestParam String instanceId,
                             @Parameter(name = "删除原因") @RequestParam(required = false) String deleteReason) {
        flowInstanceService.delete(instanceId,deleteReason);
        return new ResponseEntity();
    }
}
