package com.iscas.biz.flowable.mapper;

import com.iscas.biz.flowable.domain.dto.FlowProcDefDto;

import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 10:45
 * @since jdk11
 */
public interface FlowDeployMapper {
    /**
     * 流程定义列表
     * @param name name
     * @return List<FlowProcDefDto>
     */
    List<FlowProcDefDto> selectDeployList(String name);
}
