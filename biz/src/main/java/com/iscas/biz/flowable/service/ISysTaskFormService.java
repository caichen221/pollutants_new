package com.iscas.biz.flowable.service;

import com.iscas.biz.flowable.domain.entity.SysTaskForm;

import java.util.List;

/**
 * 流程任务关联单Service接口
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:59
 * @since jdk11
 */
@SuppressWarnings(value = "unused")
public interface ISysTaskFormService {
    /**
     * 查询流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 流程任务关联单
     */
    SysTaskForm selectSysTaskFormById(Long id);

    /**
     * 查询流程任务关联单列表
     *
     * @param sysTaskForm 流程任务关联单
     * @return 流程任务关联单集合
     */
    List<SysTaskForm> selectSysTaskFormList(SysTaskForm sysTaskForm);

    /**
     * 新增流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int insertSysTaskForm(SysTaskForm sysTaskForm);

    /**
     * 修改流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int updateSysTaskForm(SysTaskForm sysTaskForm);

    /**
     * 批量删除流程任务关联单
     *
     * @param ids 需要删除的流程任务关联单ID
     * @return 结果
     */
    int deleteSysTaskFormByIds(Long[] ids);

    /**
     * 删除流程任务关联单信息
     *
     * @param id 流程任务关联单ID
     * @return 结果
     */
    int deleteSysTaskFormById(Long id);
}
