package com.iscas.biz.flowable.service;

import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.templet.common.ResponseEntity;
import org.flowable.task.api.Task;

import java.io.InputStream;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:49
 * @since jdk11
 */
@SuppressWarnings(value = {"unused", "rawtypes"})
public interface IFlowTaskService {

    /**
     * 审批任务
     *
     * @param task 请求实体参数
     * @return ResponseEntity
     */
    ResponseEntity complete(FlowTaskVo task);

    /**
     * 驳回任务
     *
     * @param flowTaskVo flowTaskVo
     */
    void taskReject(FlowTaskVo flowTaskVo);


    /**
     * 退回任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void taskReturn(FlowTaskVo flowTaskVo);

    /**
     * 获取所有可回退的节点
     *
     * @param flowTaskVo flowTaskVo
     * @return ResponseEntity
     */
    ResponseEntity findReturnTaskList(FlowTaskVo flowTaskVo);

    /**
     * 删除任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void deleteTask(FlowTaskVo flowTaskVo);

    /**
     * 认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void claim(FlowTaskVo flowTaskVo);

    /**
     * 取消认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void unClaim(FlowTaskVo flowTaskVo);

    /**
     * 委派任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void delegateTask(FlowTaskVo flowTaskVo);


    /**
     * 转办任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void assignTask(FlowTaskVo flowTaskVo);

    /**
     * 我发起的流程
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return ResponseEntity
     */
    ResponseEntity myProcess(Integer pageNum, Integer pageSize);

    /**
     * 取消申请
     *
     * @param flowTaskVo flowTaskVo
     * @return ResponseEntity
     */
    ResponseEntity stopProcess(FlowTaskVo flowTaskVo);

    /**
     * 撤回流程
     *
     * @param flowTaskVo flowTaskVo
     * @return ResponseEntity
     */
    ResponseEntity revokeProcess(FlowTaskVo flowTaskVo);


    /**
     * 代办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return ResponseEntity
     */
    ResponseEntity todoList(Integer pageNum, Integer pageSize);


    /**
     * 已办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return ResponseEntity
     */
    ResponseEntity finishedList(Integer pageNum, Integer pageSize);

    /**
     * 流程历史流转记录
     *
     * @param procInsId 流程实例Id
     * @param deployId  deployId
     * @return ResponseEntity
     */
    ResponseEntity flowRecord(String procInsId, String deployId);

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     * @return Task
     */
    Task getTaskForm(String taskId);

    /**
     * 获取流程过程图
     *
     * @param processId processId
     * @return InputStream
     */
    InputStream diagram(String processId);

    /**
     * 获取流程执行过程
     *
     * @param procInsId procInsId
     * @return ResponseEntity
     */
    ResponseEntity getFlowViewer(String procInsId);

    /**
     * 获取流程变量
     *
     * @param taskId 任务ID
     * @return ResponseEntity
     */
    ResponseEntity processVariables(String taskId);

    /**
     * 获取下一节点
     *
     * @param flowTaskVo 任务
     * @return ResponseEntity
     */
    ResponseEntity getNextFlowNode(FlowTaskVo flowTaskVo);
}
