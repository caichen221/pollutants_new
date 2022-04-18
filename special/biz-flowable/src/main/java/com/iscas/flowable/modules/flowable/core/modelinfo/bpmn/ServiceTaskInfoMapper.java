package com.iscas.flowable.modules.flowable.core.modelinfo.bpmn;

import com.iscas.flowable.modules.flowable.core.modelinfo.AbstractInfoMapper;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.ServiceTask;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:25
 * @since jdk11
 */
@SuppressWarnings("unused")
public class ServiceTaskInfoMapper extends AbstractInfoMapper {
    @Override
    protected void mapProperties(Object element) {
        ServiceTask serviceTask = (ServiceTask) element;
        if (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equals(serviceTask.getImplementationType())) {
            createPropertyNode("Class", serviceTask.getImplementation());
        } else if (ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equals(serviceTask.getImplementationType())) {
            createPropertyNode("Expression", serviceTask.getImplementation());
        } else if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equals(serviceTask.getImplementationType())) {
            createPropertyNode("Delegate expression", serviceTask.getImplementation());
        }
        createPropertyNode("Result variable name", serviceTask.getResultVariableName());
        createPropertyNode("Use local scope for result variable", serviceTask.isUseLocalScopeForResultVariable());
    }
}
