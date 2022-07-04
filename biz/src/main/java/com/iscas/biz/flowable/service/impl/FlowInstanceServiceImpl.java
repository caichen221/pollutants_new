package com.iscas.biz.flowable.service.impl;

import com.iscas.base.biz.util.AuthContextHolder;
import com.iscas.biz.flowable.condition.ConditionalOnFlowable;
import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.biz.flowable.factory.FlowServiceFactory;
import com.iscas.biz.flowable.service.IFlowInstanceService;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseRuntimeException;
import com.iscas.templet.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 工作流流程实例管理
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 11:07
 * @since jdk11
 */
@Service
@Slf4j
@ConditionalOnFlowable
@SuppressWarnings(value = {"unused", "rawtypes"})
public class FlowInstanceServiceImpl extends FlowServiceFactory implements IFlowInstanceService {

    @Override
    public List<Task> queryListByInstanceId(String instanceId) {
        return taskService.createTaskQuery().processInstanceId(instanceId).active().list();
    }

    /**
     * 结束流程实例
     *
     * @param vo vo
     */
    @Override
    public void stopProcessInstance(FlowTaskVo vo) {
        String taskId = vo.getTaskId();
    }

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    @Override
    public void updateState(Integer state, String instanceId) {

        // 激活
        if (state == 1) {
            runtimeService.activateProcessInstanceById(instanceId);
        }
        // 挂起
        if (state == 2) {
            runtimeService.suspendProcessInstanceById(instanceId);
        }
    }

    /**
     * 删除流程实例ID
     *
     * @param instanceId   流程实例ID
     * @param deleteReason 删除原因
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String instanceId, String deleteReason) {

        // 查询历史数据
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(instanceId);
        if (historicProcessInstance.getEndTime() != null) {
            historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
            return;
        }
        // 删除流程实例
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
        // 删除历史流程实例
        historyService.deleteHistoricProcessInstance(instanceId);
    }

    /**
     * 根据实例ID查询历史实例数据
     *
     * @param processInstanceId processInstanceId
     * @return HistoricProcessInstance
     */
    @Override
    public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new FlowableObjectNotFoundException("流程实例不存在: " + processInstanceId);
        }
        return historicProcessInstance;
    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity startProcessInstanceById(String procDefId, Map<String, Object> variables) {

        try {
            // 设置流程发起人Id到流程中
            variables.put("initiator", AuthContextHolder.getUserId());
            variables.put("_FLOWABLE_SKIP_EXPRESSION_ENABLED", true);
            runtimeService.startProcessInstanceById(procDefId, variables);
            return new ResponseEntity("流程启动成功");
        } catch (Exception e) {
            throw Exceptions.baseRuntimeException("流程启动错误");
        }
    }
}
