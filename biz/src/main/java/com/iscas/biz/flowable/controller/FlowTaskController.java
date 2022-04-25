package com.iscas.biz.flowable.controller;

import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.biz.flowable.service.IFlowTaskService;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 工作流任务管理
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 17:20
 * @since jdk11
 */
@SuppressWarnings({"rawtypes", "unused"})
@Slf4j
@Api(tags = "工作流流程任务管理")
@RestController
@RequestMapping("/flowable/task")
@RequiredArgsConstructor
public class FlowTaskController {
    private final IFlowTaskService flowTaskService;

    @ApiOperation(value = "查询我发起的流程")
    @GetMapping(value = "/myProcess")
    public ResponseEntity myProcess(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                                    @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
        return flowTaskService.myProcess(pageNum, pageSize);
    }

    @ApiOperation(value = "取消申请")
    @PostMapping(value = "/stopProcess")
    public ResponseEntity stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.stopProcess(flowTaskVo);
    }

    @ApiOperation(value = "撤回流程")
    @PostMapping(value = "/revokeProcess")
    public ResponseEntity revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.revokeProcess(flowTaskVo);
    }

    @ApiOperation(value = "获取待办列表")
    @GetMapping(value = "/todoList")
    public ResponseEntity todoList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                                   @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
        return flowTaskService.todoList(pageNum, pageSize);
    }

    @ApiOperation(value = "获取已办任务")
    @GetMapping(value = "/finishedList")
    public ResponseEntity finishedList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                                       @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize) {
        return flowTaskService.finishedList(pageNum, pageSize);
    }

    @ApiOperation(value = "流程历史流转记录")
    @GetMapping(value = "/flowRecord")
    public ResponseEntity flowRecord(@ApiParam(value = "流程定义id") String procInsId, @ApiParam(value = "deployId") String deployId) {
        return flowTaskService.flowRecord(procInsId, deployId);
    }

    @ApiOperation(value = "获取流程变量")
    @GetMapping(value = "/processVariables/{taskId}")
    public ResponseEntity processVariables(@ApiParam(value = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @ApiOperation(value = "审批任务")
    @PostMapping(value = "/complete")
    public ResponseEntity complete(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.complete(flowTaskVo);
    }

    @ApiOperation(value = "驳回任务")
    @PostMapping(value = "/reject")
    public ResponseEntity taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "退回任务")
    @PostMapping(value = "/return")
    public ResponseEntity taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReturn(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    public ResponseEntity findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.findReturnTaskList(flowTaskVo);
    }

    @ApiOperation(value = "删除任务")
    @PostMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "认领/签收任务")
    @PostMapping(value = "/claim")
    public ResponseEntity claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    public ResponseEntity unClaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unClaim(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    public ResponseEntity delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "转办任务")
    @PostMapping(value = "/assign")
    public ResponseEntity assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return new ResponseEntity();
    }

    @ApiOperation(value = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
    public ResponseEntity getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo);
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取流程执行过程
     *
     * @param procInsId 任务ID
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public ResponseEntity getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }

}
