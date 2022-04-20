package com.iscas.biz.flowable.service;

import com.iscas.biz.flowable.domain.entity.SysDeployForm;
import com.iscas.biz.flowable.domain.entity.SysForm;

import java.util.List;

/**
 * 流程实例关联表单Service接口
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:53
 * @since jdk11
 */
@SuppressWarnings(value = "unused")
public interface ISysDeployFormService {
    /**
     * 查询流程实例关联表单
     *
     * @param id 流程实例关联表单ID
     * @return 流程实例关联表单
     */
    SysDeployForm selectSysDeployFormById(Long id);

    /**
     * 查询流程实例关联表单列表
     *
     * @param sysDeployForm 流程实例关联表单
     * @return 流程实例关联表单集合
     */
    List<SysDeployForm> selectSysDeployFormList(SysDeployForm sysDeployForm);

    /**
     * 新增流程实例关联表单
     *
     * @param sysDeployForm 流程实例关联表单
     * @return 结果
     */
    int insertSysDeployForm(SysDeployForm sysDeployForm);

    /**
     * 修改流程实例关联表单
     *
     * @param sysDeployForm 流程实例关联表单
     * @return 结果
     */
    int updateSysDeployForm(SysDeployForm sysDeployForm);

    /**
     * 批量删除流程实例关联表单
     *
     * @param ids 需要删除的流程实例关联表单ID
     * @return 结果
     */
     int deleteSysDeployFormByIds(Long[] ids);

    /**
     * 删除流程实例关联表单信息
     *
     * @param id 流程实例关联表单ID
     * @return 结果
     */
     int deleteSysDeployFormById(Long id);

    /**
     * 查询流程挂着的表单
     *
     * @param deployId deployId
     * @return SysForm
     */
    SysForm selectSysDeployFormByDeployId(String deployId);
}
