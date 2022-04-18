package com.iscas.flowable.modules.flowable.core.modelinfo.bpmn;

import com.iscas.flowable.modules.flowable.core.modelinfo.AbstractInfoMapper;
import org.flowable.bpmn.model.UserTask;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:21
 * @since jdk11
 */
@SuppressWarnings("unused")
public class UserTaskInfoMapper extends AbstractInfoMapper {

    @Override
    protected void mapProperties(Object element) {
        UserTask userTask = (UserTask) element;
        createPropertyNode("Assignee", userTask.getAssignee());
        createPropertyNode("Candidate users", userTask.getCandidateUsers());
        createPropertyNode("Candidate groups", userTask.getCandidateGroups());
        createPropertyNode("Due date", userTask.getDueDate());
        createPropertyNode("Form key", userTask.getFormKey());
        createPropertyNode("Priority", userTask.getPriority());
    }
}
