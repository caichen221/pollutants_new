package com.iscas.biz.flowable.controller;

import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.biz.flowable.service.IFlowInstanceService;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "工作流流程实例管理")
@RestController
@RequestMapping("/flowable/instance")
@RequiredArgsConstructor
public class FlowInstanceController {
    private final IFlowInstanceService flowInstanceService;

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/startBy/{procDefId}")
    public ResponseEntity startById(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                                    @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowInstanceService.startProcessInstanceById(procDefId, variables);

    }


    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public ResponseEntity updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
        flowInstanceService.updateState(state,instanceId);
        return new ResponseEntity();
    }

    @ApiOperation("结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    public ResponseEntity stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
        flowInstanceService.stopProcessInstance(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId,
                             @ApiParam(value = "删除原因") @RequestParam(required = false) String deleteReason) {
        flowInstanceService.delete(instanceId,deleteReason);
        return new ResponseEntity();
    }
}
