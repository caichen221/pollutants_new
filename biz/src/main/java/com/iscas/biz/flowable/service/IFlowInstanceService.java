package com.iscas.biz.flowable.service;

import com.iscas.biz.flowable.domain.vo.FlowTaskVo;
import com.iscas.templet.common.ResponseEntity;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:39
 * @since jdk11
 */
@SuppressWarnings(value = {"unused", "rawtypes"})
public interface IFlowInstanceService {

    /**
     * queryListByInstanceId
     *
     * @param instanceId instanceId
     * @return java.util.List<org.flowable.task.api.Task>
     * @date 2022/4/20
     * @since jdk11
     */
    List<Task> queryListByInstanceId(String instanceId);

    /**
     * 结束流程实例
     *
     * @param vo vo
     */
    void stopProcessInstance(FlowTaskVo vo);

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    void updateState(Integer state, String instanceId);

    /**
     * 删除流程实例ID
     *
     * @param instanceId   流程实例ID
     * @param deleteReason 删除原因
     */
    void delete(String instanceId, String deleteReason);

    /**
     * 根据实例ID查询历史实例数据
     *
     * @param processInstanceId processInstanceId
     * @return HistoricProcessInstance
     */
    HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId);

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return ResponseEntity
     */
    ResponseEntity startProcessInstanceById(String procDefId, Map<String, Object> variables);
}
