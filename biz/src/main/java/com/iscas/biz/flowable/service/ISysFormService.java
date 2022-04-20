package com.iscas.biz.flowable.service;

import com.iscas.biz.flowable.domain.entity.SysForm;

import java.util.List;

/**
 * 表单
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:58
 * @since jdk11
 */
@SuppressWarnings(value = "unused")
public interface ISysFormService {
    /**
     * 查询流程表单
     *
     * @param formId 流程表单ID
     * @return 流程表单
     */
    SysForm selectSysFormById(Long formId);

    /**
     * 查询流程表单列表
     *
     * @param sysForm 流程表单
     * @return 流程表单集合
     */
    List<SysForm> selectSysFormList(SysForm sysForm);

    /**
     * 新增流程表单
     *
     * @param sysForm 流程表单
     * @return 结果
     */
    int insertSysForm(SysForm sysForm);

    /**
     * 修改流程表单
     *
     * @param sysForm 流程表单
     * @return 结果
     */
    int updateSysForm(SysForm sysForm);

    /**
     * 批量删除流程表单
     *
     * @param formIds 需要删除的流程表单ID
     * @return 结果
     */
    int deleteSysFormByIds(Long[] formIds);

    /**
     * 删除流程表单信息
     *
     * @param formId 流程表单ID
     * @return 结果
     */
    int deleteSysFormById(Long formId);
}
