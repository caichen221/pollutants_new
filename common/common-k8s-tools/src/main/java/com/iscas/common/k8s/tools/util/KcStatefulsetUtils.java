package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.cfg.K8sConstants;
import com.iscas.common.k8s.tools.exception.K8sClientException;
import com.iscas.common.k8s.tools.model.KcContainer;
import com.iscas.common.k8s.tools.model.KcRuntimeInfo;
import com.iscas.common.k8s.tools.model.deployment.KcDepBaseInfo;
import com.iscas.common.k8s.tools.model.statefulset.KcStatefulset;
import com.iscas.common.k8s.tools.model.volume.KcVolume;
import com.iscas.common.k8s.tools.model.volume.KcVolumeClaimTemplate;
import com.iscas.common.tools.core.date.DateSafeUtils;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.AppsAPIGroupDSL;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.RollableScalableResource;
import lombok.Cleanup;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 有状态服务工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/22 16:28
 * @since jdk1.8
 */
public class KcStatefulsetUtils {
    private KcStatefulsetUtils() {}

    /**
     * 获取statefulset的信息
     * @version 1.0
     * @since jdk1.8
     * @date 2021/03/22
     * @param namespace 命名空间
     * @throws
     * @return
     */
    public static List<KcStatefulset> getStatefulset(String namespace) throws K8sClientException {

        List<KcStatefulset> kcStatefulsets = null;
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        AppsAPIGroupDSL apps = kc.apps();
        if (apps != null) {
            NonNamespaceOperation<StatefulSet, StatefulSetList, RollableScalableResource<StatefulSet>> statefulsets = apps.statefulSets().inNamespace(namespace);
            if (statefulsets != null) {
                StatefulSetList statefulSetList = statefulsets.list();
                if (statefulSetList != null) {
                    List<StatefulSet> items = statefulSetList.getItems();
                    if (CollectionUtils.isNotEmpty(items)) {
                        kcStatefulsets = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            String name = null;
                            Integer currentRepSum = null;
                            Integer planRepSum = null;
                            String runtimeStr = null;
                            List<KcRuntimeInfo> runtimeInfos = null;
                            KcDepBaseInfo baseInfo = null;

                            KcStatefulset kcStatefulset = new KcStatefulset();
                            StatefulSet statefulSet = items.get(i);

                            ObjectMeta metadata = statefulSet.getMetadata();
                            if (metadata != null) {
                                //获取name
                                name = metadata.getName();
                                //获取运行时间
                                String creationTimestamp = metadata.getCreationTimestamp();
                                Date startTime = null;
                                try {
                                    startTime = DateSafeUtils.parse(creationTimestamp, K8sConstants.TIME_PATTERN);
                                    startTime = CommonUtils.timeOffset(startTime);
                                } catch (ParseException e) {
                                    throw new K8sClientException("时间类型转换出错", e);
                                }
                                runtimeStr = CommonUtils.getTimeDistance(startTime);
                            }

                            StatefulSetStatus status = statefulSet.getStatus();
                            if (status != null) {
                                planRepSum = status.getReplicas();
                                currentRepSum = status.getReadyReplicas();
                            }

                            //获取基本信息
                            baseInfo = setBaseInfo(statefulSet);

                            setVolumns(statefulSet, kcStatefulset);

                            //获取运行时信息
                            runtimeInfos  = setRuntimeInfo(statefulSet);

                            kcStatefulset.setCurrentRepSum(currentRepSum)
                                    .setName(name)
                                    .setPlanRepSum(planRepSum)
                                    .setRuntimeStr(runtimeStr)
                                    .setBaseInfo(baseInfo)
                                    .setRuntimeInfos(runtimeInfos);
                            kcStatefulset.setStatefulSetItem(statefulSet);

                            //获取存储卷声明
                            List<KcVolumeClaimTemplate> kcVolumeClaimTemplates = setVolumeClaimTemplate(statefulSet);
                            kcStatefulset.setVolumeClaimTemplates(kcVolumeClaimTemplates);
                            kcStatefulsets.add(kcStatefulset);
                        }
                    }
                }
            }
        }
        return kcStatefulsets;
    }


    /**
     * 创建一个Statefulset
     * */
    public static void createStatefulset(KcStatefulset kcStatefulset) throws K8sClientException {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        KcDepBaseInfo baseInfo = kcStatefulset.getBaseInfo();
        List<KcVolume> volumes = kcStatefulset.getVolumes();

        //命名空间
        String namespace  = baseInfo.getNamespace();
        String name = baseInfo.getName();

        //如果存在，删除这个statefulset
//        deleteStatefulset(namespace, name);

        Map<String, String> labelMap = KcDeploymentUtils.getLabelMap(baseInfo);
        Map<String, String> annotationMap = KcDeploymentUtils.getAnnotationMap(baseInfo);

        List<KcContainer> initContainers = kcStatefulset.getInitContainer();
        List<KcContainer> containers = kcStatefulset.getContainers();
        String imagePullSecret = kcStatefulset.getImagePullSecret();

        AppsAPIGroupDSL apps = kc.apps();
        if (apps == null) {
            throw new K8sClientException("获取不到apps，无法新建statefulset");
        }
        Namespace ns = kc.namespaces().withName(namespace).get();
        if (ns == null) {
            throw new K8sClientException("获取不到命名空间，无法新建statefulset");
        }

        StatefulSet statefulSet = new StatefulSet();
        //meta
        ObjectMeta objectMeta = KcDeploymentUtils.createObjectMeta(name, namespace, labelMap, annotationMap);
        statefulSet.setMetadata(objectMeta);

        //spec
        StatefulSetSpec statefulSetSpec = new StatefulSetSpec();

        //replics
        statefulSetSpec.setReplicas(baseInfo.getPlanRepSum());

        //selector
        LabelSelector labelSelector = new LabelSelector();
        labelSelector.setMatchLabels(labelMap);
        statefulSetSpec.setSelector(labelSelector);

        //template
        PodTemplateSpec podTemplateSpec = new PodTemplateSpec();

        //template-metaData
        ObjectMeta templateMeta = new ObjectMeta();
        templateMeta.setLabels(labelMap);
        templateMeta.setAnnotations(annotationMap);
        podTemplateSpec.setMetadata(templateMeta);

        //template-spec
        PodSpec podSpec = new PodSpec();

        //template-spec-镜像密钥
        if (StringUtils.isNotEmpty(imagePullSecret)) {
            LocalObjectReference localObjectReference = new LocalObjectReference();
            localObjectReference.setName(imagePullSecret);
            podSpec.setImagePullSecrets(Arrays.asList(localObjectReference));
        }

        //template-spec-init container
        List<Container> initPodContainers = new ArrayList<>();
        if (initContainers != null) {
            //构建初始化container
            for (KcContainer initContainer : initContainers) {
                Container container = new Container();
                initPodContainers.add(container);
                KcDeploymentUtils.setContainer(initContainer, container);
            }
        }
        podSpec.setInitContainers(initPodContainers);

        List<Container> podContainers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(containers)) {
            //构建镜像
            for (KcContainer container : containers) {
                Container podContainner = new Container();
                podContainers.add(podContainner);
                KcDeploymentUtils.setContainer(container, podContainner);
            }
        }
        podSpec.setContainers(podContainers);

        //存储卷
        podSpec.setVolumes(KcDeploymentUtils.createVolume(volumes));

        podTemplateSpec.setSpec(podSpec);

        statefulSetSpec.setTemplate(podTemplateSpec);

        statefulSet.setSpec(statefulSetSpec);

        //构建存储卷声明
        List<KcVolumeClaimTemplate> volumeClaimTemplates = kcStatefulset.getVolumeClaimTemplates();
        if (CollectionUtils.isNotEmpty(volumeClaimTemplates)) {
            List<PersistentVolumeClaim> persistentVolumeClaims = volumeClaimTemplates.stream()
                    .map(vct -> {
                        PersistentVolumeClaim persistentVolumeClaim = new PersistentVolumeClaim();
                        PersistentVolumeClaimSpec persistentVolumeClaimSpec = new PersistentVolumeClaimSpec();
                        persistentVolumeClaimSpec.setAccessModes(vct.getAccessModes());
                        persistentVolumeClaimSpec.setStorageClassName(vct.getStorageClass());
                        if (vct.getStorage() != 0) {
                           ResourceRequirements resourceRequirements = new ResourceRequirements();
                           Map<String, Quantity> requests = new HashMap<>();
                           requests.put("storage", Quantity.parse(((Double) vct.getStorage()).intValue() + "Gi"));
                           resourceRequirements.setRequests(requests);
                            persistentVolumeClaimSpec.setResources(resourceRequirements);
                        }
                        ObjectMeta meta = new ObjectMeta();
                        meta.setName(vct.getName());
                        persistentVolumeClaim.setMetadata(meta);
                        persistentVolumeClaim.setSpec(persistentVolumeClaimSpec);
                        return persistentVolumeClaim;
                    }).collect(Collectors.toList());
            statefulSet.getSpec().setVolumeClaimTemplates(persistentVolumeClaims);
        }
        RollableScalableResource<StatefulSet> statefulSetRollableScalableResource = apps.statefulSets().inNamespace(namespace).withName(name);
        if (statefulSetRollableScalableResource.get() != null) {
            //编辑
            statefulSetRollableScalableResource.edit(n -> statefulSet);
        } else {
            //新增
            apps.statefulSets().inNamespace(namespace).create(statefulSet);
        }
    }

    public static void deleteStatefulset(String namespace, String name) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        AppsAPIGroupDSL apps = kc.apps();
        if (apps != null) {
            NonNamespaceOperation<StatefulSet, StatefulSetList, RollableScalableResource<StatefulSet>> statefulsetResource = apps.statefulSets().inNamespace(namespace);
            if (statefulsetResource != null) {
                RollableScalableResource<StatefulSet> rollableScalableResource = statefulsetResource.withName(name);
                if (rollableScalableResource != null && rollableScalableResource.get() != null) {
                    rollableScalableResource.delete();
                }
            }
        }
    }


    private static List<KcVolumeClaimTemplate> setVolumeClaimTemplate(StatefulSet statefulSet) {
        List<KcVolumeClaimTemplate> kcVolumeClaimTemplates = new ArrayList<>();
        StatefulSetSpec spec = statefulSet.getSpec();
        List<PersistentVolumeClaim> volumeClaimTemplates = spec.getVolumeClaimTemplates();
        if (volumeClaimTemplates != null) {
            for (PersistentVolumeClaim volumeClaimTemplate : volumeClaimTemplates) {
                KcVolumeClaimTemplate kcVolumeClaimTemplate = new KcVolumeClaimTemplate();
                ObjectMeta metadata = volumeClaimTemplate.getMetadata();
                kcVolumeClaimTemplate.setName(metadata.getName());
                PersistentVolumeClaimSpec spec1 = volumeClaimTemplate.getSpec();
                List<String> accessModes = spec1.getAccessModes();
                kcVolumeClaimTemplate.setAccessModes(accessModes);
                String storageClassName = spec1.getStorageClassName();
                kcVolumeClaimTemplate.setStorageClass(storageClassName);
                ResourceRequirements resources = spec1.getResources();
                if (resources != null) {
                    Map<String, Quantity> requests = resources.getRequests();
                    if (requests != null) {
                        Quantity storage = requests.get("storage");
                        //todo
                        kcVolumeClaimTemplate.setStorage(Double.parseDouble(storage.getAmount()));
                    }

                }
                kcVolumeClaimTemplates.add(kcVolumeClaimTemplate);
            }
        }
        return kcVolumeClaimTemplates;
    }


    /**
     * 设置运行时信息
     * */
    private static List<KcRuntimeInfo> setRuntimeInfo(StatefulSet statefulSet) throws K8sClientException {
        List<KcRuntimeInfo> kcConditions = null;
        StatefulSetStatus depStatus = statefulSet.getStatus();
        if (depStatus != null) {
            List<StatefulSetCondition> conditions = depStatus.getConditions();
            if (CollectionUtils.isNotEmpty(conditions)) {
                kcConditions  = new ArrayList<>();
                for (StatefulSetCondition condition : conditions) {
                    KcRuntimeInfo kcCondtion = new KcRuntimeInfo();
                    String type = null;
                    String status = null;
                    Date lastUpdateTime = null;
                    Date lastTransationTime = null;
                    String reason = null;
                    String message = null;

                    type = condition.getType();
                    status = condition.getStatus();
                    try {

                        lastUpdateTime = DateSafeUtils.parse(condition.getLastTransitionTime(), K8sConstants.TIME_PATTERN);
                        lastUpdateTime = CommonUtils.timeOffset(lastUpdateTime);
                        lastTransationTime = DateSafeUtils.parse(condition.getLastTransitionTime(), K8sConstants.TIME_PATTERN);
                        lastTransationTime = CommonUtils.timeOffset(lastTransationTime);
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
    private static KcDepBaseInfo setBaseInfo(StatefulSet statefulSet) {
        KcDepBaseInfo baseInfo = new KcDepBaseInfo();
        String type = "statefulset";
        String name = null;
        List<String[]> labels = new ArrayList<>();
        List<String[]> annotations = new ArrayList<>();
        String description = null;
        Integer currentRepSum = null;
        Integer planRepSum = null;
        String namespace = null;

        ObjectMeta metadata = statefulSet.getMetadata();

        Map<String, Object> metaDataResultMap = KcDeploymentUtils.setObjectMeta(metadata);
        name = (String) metaDataResultMap.get("name");
        namespace = (String) metaDataResultMap.get("namespace");
        annotations = (List<String[]>) metaDataResultMap.get("annotations");

        StatefulSetSpec spec = statefulSet.getSpec();
        List<String[]> matchLabels = new ArrayList<>();
        LabelSelector selector = spec.getSelector();
        PodTemplateSpec template = spec.getTemplate();
        Map<String, Object> specResultMap = KcDeploymentUtils.setSpec(selector, template);
        matchLabels = (List<String[]>) specResultMap.get("matchLabels");
        labels = (List<String[]>) specResultMap.get("labels");

        StatefulSetStatus status = statefulSet.getStatus();
        if (status != null) {
            planRepSum = status.getReplicas();
            currentRepSum = status.getReadyReplicas();
        }
        baseInfo.setType(type)
                .setName(name)
                .setDescription(description)
                .setLabels(labels)
                .setMatchLabels(matchLabels)
                .setAnnotations(annotations)
                .setPlanRepSum(planRepSum)
                .setNamespace(namespace)
                .setCurrentRepSum(currentRepSum);
        return baseInfo;
    }

    private static void setVolumns(StatefulSet statefulSet, KcStatefulset kcStatefulset) {
        StatefulSetSpec spec = statefulSet.getSpec();
        if (spec != null) {
            PodTemplateSpec template = spec.getTemplate();
            if (template != null) {
                PodSpec templateSpec = template.getSpec();
                if (templateSpec != null) {
                    List<Volume> volumes = templateSpec.getVolumes();
                    if (CollectionUtils.isNotEmpty(volumes)) {
                        for (Volume volume : volumes) {
                            KcVolume kcVolume = KcDeploymentUtils.setOneVolume(volume);
                            kcStatefulset.getVolumes().add(kcVolume);
                        }
                    }
                }
            }
        }
    }


    public static void restartStatefulset(String namespace, String name) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        kc.apps().statefulSets().inNamespace(namespace).withName(name)
                .rolling()
                .restart();
    }

    /**
     * 伸缩
     * */
    public static void scale(String namespace, String name, Integer maxReplicas) throws K8sClientException {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        AppsAPIGroupDSL apps = kc.apps();

        if (apps != null) {
            MixedOperation<StatefulSet, StatefulSetList, RollableScalableResource<StatefulSet>> statefulsets = apps.statefulSets();
            if (statefulsets != null) {
                NonNamespaceOperation<StatefulSet, StatefulSetList, RollableScalableResource<StatefulSet>> nonNamespaceOperation = statefulsets.inNamespace(namespace);
                if (nonNamespaceOperation != null) {
                    RollableScalableResource<StatefulSet> rollableScalableResource = nonNamespaceOperation.withName(name);
                    if (rollableScalableResource != null) {
                        rollableScalableResource.scale(maxReplicas);
                    } else {
                        throw new K8sClientException(String.format("找不到命名空间:[%s]内的服务:[%s]", namespace, name));
                    }
                } else {
                    throw new K8sClientException(String.format("找不到命名空间:[%s]内的服务:[%s]", namespace, name));
                }

            } else {
                throw new K8sClientException(String.format("找不到命名空间:[%s]内的服务:[%s]", namespace, name));
            }
        } else {
            throw new K8sClientException(String.format("找不到命名空间:[%s]内的服务:[%s]", namespace, name));
        }
    }
}
