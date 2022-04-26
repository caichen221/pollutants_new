package com.iscas.biz.flowable.service.impl;

import com.iscas.biz.flowable.condition.ConditionalOnFlowable;
import com.iscas.biz.flowable.domain.entity.SysTaskForm;
import com.iscas.biz.flowable.mapper.SysTaskFormMapper;
import com.iscas.biz.flowable.service.ISysTaskFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 14:09
 * @since jdk11
 */
@Service
@ConditionalOnFlowable
@SuppressWarnings("unused")
public class SysTaskFormServiceImpl implements ISysTaskFormService {
    @Autowired
    private SysTaskFormMapper sysTaskFormMapper;

    /**
     * 查询流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 流程任务关联单
     */
    @Override
    public SysTaskForm selectSysTaskFormById(Long id) {
        return sysTaskFormMapper.selectSysTaskFormById(id);
    }

    /**
     * 查询流程任务关联单列表
     *
     * @param sysTaskForm 流程任务关联单
     * @return 流程任务关联单
     */
    @Override
    public List<SysTaskForm> selectSysTaskFormList(SysTaskForm sysTaskForm) {
        return sysTaskFormMapper.selectSysTaskFormList(sysTaskForm);
    }

    /**
     * 新增流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    @Override
    public int insertSysTaskForm(SysTaskForm sysTaskForm) {
        return sysTaskFormMapper.insertSysTaskForm(sysTaskForm);
    }

    /**
     * 修改流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    @Override
    public int updateSysTaskForm(SysTaskForm sysTaskForm) {
        return sysTaskFormMapper.updateSysTaskForm(sysTaskForm);
    }

    /**
     * 批量删除流程任务关联单
     *
     * @param ids 需要删除的流程任务关联单ID
     * @return 结果
     */
    @Override
    public int deleteSysTaskFormByIds(Long[] ids) {
        return sysTaskFormMapper.deleteSysTaskFormByIds(ids);
    }

    /**
     * 删除流程任务关联单信息
     *
     * @param id 流程任务关联单ID
     * @return 结果
     */
    @Override
    public int deleteSysTaskFormById(Long id) {
        return sysTaskFormMapper.deleteSysTaskFormById(id);
    }
}
