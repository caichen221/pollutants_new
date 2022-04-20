package com.iscas.biz.flowable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.flowable.domain.dto.FlowProcDefDto;
import com.iscas.templet.common.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:27
 * @since jdk11
 */
@SuppressWarnings({"unused", "rawtypes"})
public interface IFlowDefinitionService {

    /**
     * 是否存在
     *
     * @param processDefinitionKey processDefinitionKey
     * @return boolean
     * @date 2022/4/20
     * @since jdk11
     */
    boolean exist(String processDefinitionKey);


    /**
     * 流程定义列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param name     name
     * @return 流程定义分页列表数据
     */
    Page<FlowProcDefDto> list(String name, Integer pageNum, Integer pageSize);

    /**
     * 导入流程文件
     *
     * @param name     name
     * @param category category
     * @param in       in
     */
    void importFile(String name, String category, InputStream in);

    /**
     * 读取xml
     *
     * @param deployId ID
     * @return ResponseEntity
     * @throws IOException io异常
     */
    ResponseEntity readXml(String deployId) throws IOException;

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId procDefId
     * @param variables variables
     * @return ResponseEntity
     */
    ResponseEntity startProcessInstanceById(String procDefId, Map<String, Object> variables);


    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    void updateState(Integer state, String deployId);


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    void delete(String deployId);


    /**
     * 读取图片文件
     *
     * @param deployId deployId
     * @return InputStream
     */
    InputStream readImage(String deployId);
}
