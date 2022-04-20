package com.iscas.biz.flowable.mapper;

import com.iscas.biz.flowable.domain.entity.SysDeployForm;
import com.iscas.biz.flowable.domain.entity.SysForm;

import java.util.List;

/**
 * 流程实例关联表单Mapper接口
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 13:55
 * @since jdk11
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public interface SysDeployFormMapper {
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
     * @param SysDeployForm 流程实例关联表单
     * @return 流程实例关联表单集合
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    List<SysDeployForm> selectSysDeployFormList(SysDeployForm SysDeployForm);

    /**
     * 新增流程实例关联表单
     *
     * @param SysDeployForm 流程实例关联表单
     * @return 结果
     */
    int insertSysDeployForm(SysDeployForm SysDeployForm);

    /**
     * 修改流程实例关联表单
     *
     * @param SysDeployForm 流程实例关联表单
     * @return 结果
     */
    int updateSysDeployForm(SysDeployForm SysDeployForm);

    /**
     * 删除流程实例关联表单
     *
     * @param id 流程实例关联表单ID
     * @return 结果
     */
    int deleteSysDeployFormById(Long id);

    /**
     * 批量删除流程实例关联表单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSysDeployFormByIds(Long[] ids);



    /**
     * 查询流程挂着的表单
     * @param deployId deployId
     * @return SysForm
     */
    SysForm selectSysDeployFormByDeployId(String deployId);
}
