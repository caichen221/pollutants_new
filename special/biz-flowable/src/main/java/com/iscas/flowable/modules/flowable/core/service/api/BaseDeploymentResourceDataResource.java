package com.iscas.flowable.modules.flowable.core.service.api;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:26
 * @since jdk11
 */
@SuppressWarnings("unused")
public class BaseDeploymentResourceDataResource {
    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    protected BpmnModel getBpmnModel(String processDefinitionId) {
        ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);
        byte[] resData = getDeploymentResourceData(processDefinition.getDeploymentId(), processDefinition.getResourceName());

        try {
            InputStream responseContent = new ByteArrayInputStream(resData);
            InputStreamReader in = new InputStreamReader(responseContent, StandardCharsets.UTF_8);
            XMLStreamReader xtr = XMLInputFactory.newInstance().createXMLStreamReader(in);

            return new BpmnXMLConverter().convertToBpmnModel(xtr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected byte[] getDeploymentResourceData(String deploymentId, String resourceName/*, HttpServletResponse response*/) {

        if (deploymentId == null) {
            throw new FlowableIllegalArgumentException("No deployment id provided");
        }
        if (resourceName == null) {
            throw new FlowableIllegalArgumentException("No resource name provided");
        }

        // Check if deployment exists
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            throw new FlowableObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.", Deployment.class);
        }

        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);

        if (resourceList.contains(resourceName)) {
            final InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceName);

            try {

                return IOUtils.toByteArray(resourceStream);

            } catch (Exception e) {
                throw new FlowableException("Error converting resource stream", e);
            }
        } else {
            // Resource not found in deployment
            throw new FlowableObjectNotFoundException("Could not find a resource with name '" + resourceName + "' in deployment '" + deploymentId + "'.", String.class);
        }
    }

    protected ProcessDefinition getProcessDefinitionFromRequest(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

        if (processDefinition == null) {
            throw new FlowableObjectNotFoundException("Could not find a process definition with id '" + processDefinitionId + "'.", ProcessDefinition.class);
        }
        return processDefinition;
    }


    protected Execution getExecutionFromRequest(String executionId) {
        Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        if (execution == null) {
            throw new FlowableObjectNotFoundException("Could not find an execution with id '" + executionId + "'.", Execution.class);
        }
        return execution;
    }
}
