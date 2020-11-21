package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.exception.K8sClientException;
import com.iscas.common.k8s.tools.model.KcContainer;
import com.iscas.common.k8s.tools.model.KcContainerPort;
import com.iscas.common.k8s.tools.model.KcResource;
import com.iscas.common.k8s.tools.model.KcVolumeMounts;
import com.iscas.common.k8s.tools.model.deployment.KcDepBaseInfo;
import com.iscas.common.k8s.tools.model.deployment.KcDepRuntimeInfo;
import com.iscas.common.k8s.tools.model.deployment.KcDeployment;
import com.iscas.common.k8s.tools.model.health.KcHealthTcpParam;
import com.iscas.common.k8s.tools.model.health.KcLivenessProbe;
import com.iscas.common.k8s.tools.model.health.KcReadinessProbe;
import com.iscas.common.tools.core.date.DateSafeUtils;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.AppsAPIGroupDSL;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.RollableScalableResource;
import lombok.Cleanup;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/8 17:30
 * @since jdk1.8
 */
public class KcDeploymentUtils {
    private KcDeploymentUtils() {}


    private static void setContainer(KcContainer container,
                                      PodSpecFluent.ContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>> specNestedInitContainer) throws K8sClientException {


        specNestedInitContainer.withNewName(container.getName())
                .withNewImage(container.getImage())
                .withNewImagePullPolicy(container.getImagePullPolicy());
        //命令
        List<String> commands = container.getCommands();
        //参数
        List<String> args = container.getArgs();
        //环境变量
        LinkedHashMap<String, String> env = container.getEnv();
        //挂载点
        List<KcVolumeMounts> volumeMounts = container.getVolumeMounts();
        //就绪检查
        KcReadinessProbe readinessProbe = container.getReadinessProbe();
        //存活检查
        KcLivenessProbe livenessProbe = container.getLivenessProbe();

        //资源限制配置
        KcResource resource = container.getResource();

        //端口
        List<KcContainerPort> ports = container.getPorts();

        if (CollectionUtils.isNotEmpty(commands)) {
            for (String command : commands) {
                specNestedInitContainer.addNewCommand(command);
            }
        }
        if (CollectionUtils.isNotEmpty(args)) {
            for (String arg : args) {
                specNestedInitContainer.addNewArg(arg);
            }
        }
        if (MapUtils.isNotEmpty(env)) {
            for (Map.Entry<String ,String> entry : env.entrySet()) {
                specNestedInitContainer.addNewEnv()
                        .withNewName(entry.getKey())
                        .withNewValue(entry.getValue())
                        .endEnv();
            }

        }
        if (CollectionUtils.isNotEmpty(volumeMounts)) {
            for (KcVolumeMounts volumeMount : volumeMounts) {
                specNestedInitContainer.addNewVolumeMount()
                        .withNewMountPath(volumeMount.getMountPath())
                        .withNewName(volumeMount.getName())
                        .withNewSubPath(volumeMount.getSubPath() == null ? volumeMount.getMountPath() : volumeMount.getSubPath())
                        .withNewReadOnly(volumeMount.isReadOnly())
                        .endVolumeMount();
            }
        }
        if (readinessProbe != null) {
            String type = readinessProbe.getType();
            if (!"TCP".equalsIgnoreCase(type)) {
                throw new K8sClientException("暂时只支持TCP的健康检查");
            }
            KcHealthTcpParam tcpParam = (KcHealthTcpParam) readinessProbe.getHealthParam();
            specNestedInitContainer.withNewReadinessProbe()
                    .withNewTcpSocket()
                    .withNewPort(tcpParam.getPort())
                    .endTcpSocket()
                    .withTimeoutSeconds(tcpParam.getTimeout())
                    .withInitialDelaySeconds(tcpParam.getInitialDelaySeconds())
                    .withPeriodSeconds(tcpParam.getPeriodSeconds())
                    .withSuccessThreshold(tcpParam.getHealthThreshold())
                    .withFailureThreshold(tcpParam.getUnHealthThreshold())
                    .endReadinessProbe();
        }

        if (livenessProbe != null) {
            String type = livenessProbe.getType();
            if (!"TCP".equalsIgnoreCase(type)) {
                throw new K8sClientException("暂时只支持TCP的健康检查");
            }
            KcHealthTcpParam tcpParam = (KcHealthTcpParam) livenessProbe.getHealthParam();
            specNestedInitContainer.withNewLivenessProbe()
                    .withNewTcpSocket()
                    .withNewPort(tcpParam.getPort())
                    .endTcpSocket()
                    .withTimeoutSeconds(tcpParam.getTimeout())
                    .withInitialDelaySeconds(tcpParam.getInitialDelaySeconds())
                    .withPeriodSeconds(tcpParam.getPeriodSeconds())
                    .withSuccessThreshold(tcpParam.getHealthThreshold())
                    .withFailureThreshold(tcpParam.getUnHealthThreshold())
                    .endLivenessProbe();
        }
        if (CollectionUtils.isNotEmpty(ports)) {
            for (KcContainerPort port : ports) {
                ContainerFluent.PortsNested<PodSpecFluent.ContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>>> containersNestedPortsNested = specNestedInitContainer.addNewPort();
                if (StringUtils.isNotEmpty(port.getName())) {
                    containersNestedPortsNested.withNewName(port.getName());
                }
                if (port.getContainerPort() != null) {
                    containersNestedPortsNested.withContainerPort(port.getContainerPort());
                }

                if (port.getHostPort() != null) {
                    containersNestedPortsNested.withHostPort(port.getHostPort());
                }
                specNestedInitContainer = containersNestedPortsNested.endPort();
            }
        }


        if (resource != null) {
//            specNestedInitContainer.withNewResources()
//                    .addToLimits()
//                    .addToRequests()
//                    .endResources();
        }

    }


    private static void setInitContainer(KcContainer container,
                              PodSpecFluent.InitContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>> specNestedInitContainer) throws K8sClientException {
        specNestedInitContainer.withNewName(container.getName())
                .withNewImage(container.getImage())
                .withNewImagePullPolicy(container.getImagePullPolicy());
        //命令
        List<String> commands = container.getCommands();
        //参数
        List<String> args = container.getArgs();
        //环境变量
        LinkedHashMap<String, String> env = container.getEnv();
        //挂载点
        List<KcVolumeMounts> volumeMounts = container.getVolumeMounts();
        //就绪检查
        KcReadinessProbe readinessProbe = container.getReadinessProbe();
        //存活检查
        KcLivenessProbe livenessProbe = container.getLivenessProbe();

        //资源限制配置
        KcResource resource = container.getResource();

        //端口
        List<KcContainerPort> ports = container.getPorts();

        if (CollectionUtils.isNotEmpty(commands)) {
            for (String command : commands) {
                specNestedInitContainer.addNewCommand(command);
            }
        }
        if (CollectionUtils.isNotEmpty(args)) {
            for (String arg : args) {
                specNestedInitContainer.addNewArg(arg);
            }
        }
        if (MapUtils.isNotEmpty(env)) {
            for (Map.Entry<String ,String> entry : env.entrySet()) {
                specNestedInitContainer.addNewEnv()
                        .withNewName(entry.getKey())
                        .withNewValue(entry.getValue())
                        .endEnv();
            }

        }
        if (CollectionUtils.isNotEmpty(volumeMounts)) {
            for (KcVolumeMounts volumeMount : volumeMounts) {
                specNestedInitContainer.addNewVolumeMount()
                        .withNewMountPath(volumeMount.getMountPath())
                        .withNewName(volumeMount.getName())
                        .withNewSubPath(volumeMount.getSubPath() == null ? volumeMount.getMountPath() : volumeMount.getSubPath())
                        .withNewReadOnly(volumeMount.isReadOnly())
                        .endVolumeMount();
            }
        }
        if (readinessProbe != null) {
            String type = readinessProbe.getType();
            if (!"TCP".equalsIgnoreCase(type)) {
                throw new K8sClientException("暂时只支持TCP的健康检查");
            }
            KcHealthTcpParam tcpParam = (KcHealthTcpParam) readinessProbe.getHealthParam();
            specNestedInitContainer.withNewReadinessProbe()
                    .withNewTcpSocket()
                    .withNewPort(tcpParam.getPort())
                    .endTcpSocket()
                    .withTimeoutSeconds(tcpParam.getTimeout())
                    .withInitialDelaySeconds(tcpParam.getInitialDelaySeconds())
                    .withPeriodSeconds(tcpParam.getPeriodSeconds())
                    .withSuccessThreshold(tcpParam.getHealthThreshold())
                    .withFailureThreshold(tcpParam.getUnHealthThreshold())
                    .endReadinessProbe();
        }

        if (livenessProbe != null) {
            String type = livenessProbe.getType();
            if (!"TCP".equalsIgnoreCase(type)) {
                throw new K8sClientException("暂时只支持TCP的健康检查");
            }
            KcHealthTcpParam tcpParam = (KcHealthTcpParam) livenessProbe.getHealthParam();
            specNestedInitContainer.withNewLivenessProbe()
                    .withNewTcpSocket()
                    .withNewPort(tcpParam.getPort())
                    .endTcpSocket()
                    .withTimeoutSeconds(tcpParam.getTimeout())
                    .withInitialDelaySeconds(tcpParam.getInitialDelaySeconds())
                    .withPeriodSeconds(tcpParam.getPeriodSeconds())
                    .withSuccessThreshold(tcpParam.getHealthThreshold())
                    .withFailureThreshold(tcpParam.getUnHealthThreshold())
                    .endLivenessProbe();
        }

        if (CollectionUtils.isNotEmpty(ports)) {
            for (KcContainerPort port : ports) {
                ContainerFluent.PortsNested<PodSpecFluent.InitContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>>> initContainersNestedPortsNested = specNestedInitContainer.addNewPort();
                if (StringUtils.isNotEmpty(port.getName())) {
                    initContainersNestedPortsNested.withNewName(port.getName());
                }
                if (port.getContainerPort() != null) {
                    initContainersNestedPortsNested.withContainerPort(port.getContainerPort());
                }

                if (port.getHostPort() != null) {
                    initContainersNestedPortsNested.withHostPort(port.getHostPort());
                }
                specNestedInitContainer = initContainersNestedPortsNested.endPort();
            }
        }

        if (resource != null) {
//            specNestedInitContainer.withNewResources()
//                    .addToLimits()
//                    .addToRequests()
//                    .endResources();
        }

    }

    /**
     * 创建一个Deployment
     * */
    public static void createDeployment(KcDeployment kcDeployment) throws K8sClientException {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        KcDepBaseInfo baseInfo = kcDeployment.getBaseInfo();

        //命名空间
        String namespace  = baseInfo.getNamespace();
        String name = baseInfo.getName();
        List<String[]> labels = baseInfo.getLabels();
        Map<String, String> labelMap = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(labels)) {
            for (String[] label : labels) {
                labelMap.put(label[0], label[1]);
            }
        }
        List<String[]> annotations = baseInfo.getAnnotations();
        Map<String, String> annotationMap = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(annotations)) {
            for (String[] annotation : annotations) {
                annotationMap.put(annotation[0], annotation[1]);
            }
        }
        KcContainer initContainer = kcDeployment.getInitContainer();
        List<KcContainer> containers = kcDeployment.getContainers();
        String imagePullSecret = kcDeployment.getImagePullSecret();

        AppsAPIGroupDSL apps = kc.apps();
        if (apps == null) {
            throw new K8sClientException("获取不到apps，无法新建deployment");
        }
        Namespace ns = kc.namespaces().withName(namespace).get();
        if (ns == null) {
            throw new K8sClientException("获取不到命名空间，无法新建deployment");
        }
        DoneableDeployment doneableDeployment = apps.deployments().inNamespace(namespace).createNew();
        //构建matadata
        doneableDeployment = doneableDeployment.withNewMetadata()
                .withNewName(name)
                .withNewNamespace(namespace)
//                .addToLabels(labelMap)
//                .addToAnnotations(annotationMap)
                .endMetadata();
        //构建最外层的spec
        DeploymentFluent.SpecNested<DoneableDeployment> doneableDeploymentSpec = doneableDeployment.withNewSpec();

        doneableDeploymentSpec.withReplicas(baseInfo.getPlanRepSum());

        doneableDeploymentSpec.withNewSelector()
                .withMatchLabels(labelMap)
                .endSelector();

        //构建template
        DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>> specNestedTemplate= doneableDeploymentSpec.withNewTemplate();
        //构建metadata
        PodTemplateSpecFluent.MetadataNested<DeploymentSpecFluent   .TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>> templateNestedMetadata = specNestedTemplate.withNewMetadata();
        templateNestedMetadata = templateNestedMetadata.addToLabels(labelMap);
        templateNestedMetadata = templateNestedMetadata.addToAnnotations(annotationMap);
        //metadata -end
        specNestedTemplate = templateNestedMetadata.endMetadata();

        //构建内层spec
        PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>> templateNestedSpecNested = specNestedTemplate.withNewSpec();

        //加入镜像拉取私钥
        if (StringUtils.isNotEmpty(imagePullSecret)) {
            templateNestedSpecNested.addNewImagePullSecret(imagePullSecret);
        }
        if (initContainer != null) {
            //构建初始化container
            PodSpecFluent.InitContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>> specNestedInitContainer = templateNestedSpecNested.addNewInitContainer();
            setInitContainer(initContainer, specNestedInitContainer);
            templateNestedSpecNested = specNestedInitContainer.endInitContainer();
        }
        if (CollectionUtils.isNotEmpty(containers)) {
            //构建镜像
            for (KcContainer container : containers) {
                PodSpecFluent.ContainersNested<PodTemplateSpecFluent.SpecNested<DeploymentSpecFluent.TemplateNested<DeploymentFluent.SpecNested<DoneableDeployment>>>> specNestedContainersNested = templateNestedSpecNested.addNewContainer();
                setContainer(container, specNestedContainersNested);
                specNestedContainersNested.endContainer();
            }
        }


        //内层spec-end
        specNestedTemplate = templateNestedSpecNested.endSpec();

        //template -end
        doneableDeploymentSpec = specNestedTemplate.endTemplate();


        // 最外层spec-end
        doneableDeployment = doneableDeploymentSpec.endSpec();
        doneableDeployment.done();

//        kc.apps().deployments().inNamespace("xxx").createNew()
//                .withNewSpec()
//                .withNewTemplate()
//                .withNewMetadata()
//                .endMetadata()
//                .withNewSpec()
////                .add
//                .endSpec()
//                .endTemplate()
//                .endSpec();
    }

    public static void deleteDeployment(String namespace, String name) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        AppsAPIGroupDSL apps = kc.apps();
        if (apps != null) {
            NonNamespaceOperation<Deployment, DeploymentList, DoneableDeployment, RollableScalableResource<Deployment, DoneableDeployment>> deployResource = apps.deployments().inNamespace(namespace);
            if (deployResource != null) {
                RollableScalableResource<Deployment, DoneableDeployment> nameResource = deployResource.withName(name);
                if (nameResource != null && nameResource.get() != null) {
                    nameResource.delete();
                }
            }
        }
    }


    /**
     * 获取deployment的信息
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/9
     * @param namespace 命名空间
     * @throws
     * @return java.util.List<com.iscas.common.k8s.tools.model.deployment.KcDeployment>
     */
    public static List<KcDeployment> getDeployments(String namespace) throws K8sClientException {

        List<KcDeployment> kcDeployments = null;
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        AppsAPIGroupDSL apps = kc.apps();
        if (apps != null) {
            NonNamespaceOperation<Deployment, DeploymentList, DoneableDeployment, RollableScalableResource<Deployment, DoneableDeployment>> deployments = apps.deployments().inNamespace(namespace);
            if (deployments != null) {
                DeploymentList deploymentList = deployments.list();
                if (deploymentList != null) {
                    List<Deployment> items = deploymentList.getItems();
                    if (CollectionUtils.isNotEmpty(items)) {
                        kcDeployments = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {

                            String name = null;
                            Integer currentRepSum = null;
                            Integer planRepSum = null;
                            String runtimeStr = null;
                            List<KcDepRuntimeInfo> runtimeInfos = null;
                            KcDepBaseInfo baseInfo = null;

                            KcDeployment kcDeployment = new KcDeployment();
                            Deployment deployment = items.get(i);

                            ObjectMeta metadata = deployment.getMetadata();
                            if (metadata != null) {
                                //获取name
                                name = metadata.getName();
                                //获取运行时间
                                String creationTimestamp = metadata.getCreationTimestamp();
                                Date startTime = null;
                                try {
                                    startTime = DateSafeUtils.parse(creationTimestamp, "yyyy-MM-dd'T'HH:mm:ss'Z'");
                                } catch (ParseException e) {
                                    throw new K8sClientException("时间类型转换出错", e);
                                }
                                runtimeStr = CommonUtils.getTimeDistance(startTime);
                            }

                            DeploymentStatus status = deployment.getStatus();
                            if (status != null) {
                                planRepSum = status.getReplicas();
                                currentRepSum = status.getReadyReplicas();
                            }

                            //获取基本信息
                            baseInfo = setBaseInfo(deployment);

                            //获取运行时信息
                            runtimeInfos  = setRuntimeInfo(deployment);

                            kcDeployment.setCurrentRepSum(currentRepSum)
                                    .setName(name)
                                    .setPlanRepSum(planRepSum)
                                    .setRuntimeStr(runtimeStr)
                                    .setBaseInfo(baseInfo)
                                    .setRuntimeInfos(runtimeInfos);
                            kcDeployments.add(kcDeployment);

                        }
                    }
                }
            }
        }
        return kcDeployments;
    }

    /**
     * 设置运行时信息
     * */
    private static List<KcDepRuntimeInfo> setRuntimeInfo(Deployment deployment) throws K8sClientException {
        List<KcDepRuntimeInfo> kcConditions = null;
        DeploymentStatus depStatus = deployment.getStatus();
        if (depStatus != null) {
            List<DeploymentCondition> conditions = depStatus.getConditions();
            if (CollectionUtils.isNotEmpty(conditions)) {
                kcConditions  = new ArrayList<>();
                for (DeploymentCondition condition : conditions) {
                    KcDepRuntimeInfo kcCondtion = new KcDepRuntimeInfo();
                    String type = null;
                    String status = null;
                    Date lastUpdateTime = null;
                    Date lastTransationTime = null;
                    String reason = null;
                    String message = null;

                    type = condition.getType();
                    status = condition.getStatus();
                    try {

                        lastUpdateTime = DateSafeUtils.parse(condition.getLastUpdateTime(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
                        lastTransationTime = DateSafeUtils.parse(condition.getLastTransitionTime(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
                    } catch (ParseException e) {
                        throw new K8sClientException("时间类型转换出错", e);
                    }
                    reason = condition.getReason();
                    message = condition.getMessage();
                    kcCondtion.setType(type)
                            .setLastTransitionTime(lastTransationTime)
                            .setLastUpdateTime(lastUpdateTime)
                            .setMessage(message)
                            .setReason(reason)
                            .setStatus(status);
                    kcConditions.add(kcCondtion);
                }
            }
        }
        return kcConditions;
    }

    /**
     *  设置deployment的基本信息
     * */
    private static KcDepBaseInfo setBaseInfo(Deployment deployment) {
        KcDepBaseInfo baseInfo = new KcDepBaseInfo();
        String type = "deployment";
        String name = null;
        List<String[]> labels = new ArrayList<>();
        List<String[]> annotations = new ArrayList<>();
        String description = null;
        Integer currentRepSum = null;
        Integer planRepSum = null;
        String namespace = null;

        ObjectMeta metadata = deployment.getMetadata();
        if (metadata != null) {
            //获取name
            name = metadata.getName();
            //获取labels
            Map<String, String> metadataLabels = metadata.getLabels();
            if (MapUtils.isNotEmpty(metadataLabels)) {
                metadataLabels.forEach((key, val) -> {
                    String[] label = new String[2];
                    label[0] = key;
                    label[1] = val;
                    labels.add(label);
                });
            }
            //获取annotations
            Map<String, String> metadataAnnotations = metadata.getAnnotations();
            if (MapUtils.isNotEmpty(metadataLabels)) {
                metadataAnnotations.forEach((key, val) -> {
                    String[] annotation = new String[2];
                    annotation[0] = key;
                    annotation[1] = val;
                    annotations.add(annotation);
                });
            }

            //获取namespace
            namespace = metadata.getNamespace();
        }

        DeploymentStatus status = deployment.getStatus();
        if (status != null) {
            planRepSum = status.getReplicas();
            currentRepSum = status.getReadyReplicas();
        }
        baseInfo.setType(type)
                .setName(name)
                .setDescription(description)
                .setLabels(labels)
                .setPlanRepSum(planRepSum)
                .setNamespace(namespace)
                .setCurrentRepSum(currentRepSum);
        return baseInfo;
    }
}
