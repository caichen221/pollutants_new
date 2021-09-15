package com.iscas.common.k8s.tools.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.iscas.common.k8s.tools.ApiPaths;
import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.K8sCustomUtils;
import com.iscas.common.k8s.tools.cfg.K8sConstants;
import com.iscas.common.k8s.tools.model.KcResource;
import com.iscas.common.k8s.tools.model.health.*;
import com.iscas.common.k8s.tools.model.pod.*;
import com.iscas.common.tools.core.date.DateSafeUtils;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.ContainerMetrics;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.PodMetrics;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.PodMetricsList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.Cleanup;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 操作POD的工具
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/31 17:52
 * @since jdk1.8
 */
public class KcPodUtils {
    private KcPodUtils() {}

    /**
     * 获取pod
     * */
    public static List<KcPod> getPods(String namespace, boolean metrics) throws ParseException, IOException {
        List<KcPod> kcPods = null;
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        NonNamespaceOperation<Pod, PodList, PodResource<Pod>> podsInNamespace = kc.pods().inNamespace(namespace);
        if (podsInNamespace != null) {
            PodList podList = podsInNamespace.list();
            if (podList != null) {
                List<Pod> items = podList.getItems();
                if (CollectionUtils.isNotEmpty(items)) {
                    //获取metrics
//                    JSONObject podMetrics = null;
                    PodMetricsList podMetrics = null;
                    if (metrics) {
//                        podMetrics = getPodsMetrics(namespace);
                        try {
                            podMetrics = kc.top().pods().metrics();
                        } catch (Exception e) {
                            System.err.println("获取pod metrics出错");
                            e.printStackTrace();
                        }
                    }

                    kcPods = new ArrayList<>();
                    for (Pod pod : items) {
                        KcPod kcPod = getOnePod(pod, podMetrics);
                        kcPods.add(kcPod);
                    }
                }
            }
        }
        return kcPods;
    }

    private static JSONObject getPodsMetrics(String namespace) throws IOException {
        String url = K8sClient.getConfig().getApiServerPath() + ApiPaths.API_PATH_METRICS_PODS;
        url = url.replaceAll("//+", "/")
                .replace("{namespace}", namespace);
        String s = K8sCustomUtils.doGet(url);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSONUtil.parseObj(s);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KcPod getOnePod(Pod pod, PodMetricsList podMetricsList) throws ParseException {
        KcPod kcPod = new KcPod();
        setBaseInfo(kcPod, pod, podMetricsList);
        setContainers(kcPod, pod, podMetricsList);
        setInitContainers(kcPod, pod, podMetricsList);
        setConditions(kcPod, pod);
        System.out.println(pod);
        return kcPod;
    }

    private static void setConditions(KcPod kcPod, Pod pod) throws ParseException {
        List<KcPodCondition> kcPodConditions = new ArrayList<>();
        List<PodCondition> conditions = pod.getStatus().getConditions();
        if (CollectionUtils.isNotEmpty(conditions)) {
            for (PodCondition condition : conditions) {
                KcPodCondition kcPodCondition = new KcPodCondition();
                kcPodCondition.setMessage(condition.getMessage())
                        .setReason(condition.getReason())
                        .setStatus(condition.getStatus())
                        .setType(condition.getType());
                String lastProbeTime = condition.getLastProbeTime();
                if (lastProbeTime != null) {
                    Date lastTime = DateSafeUtils.parse(lastProbeTime, K8sConstants.TIME_PATTERN);
                    kcPodCondition.setLastProbTime(CommonUtils.timeOffset(lastTime));
                }

                String lastTransitionTime = condition.getLastTransitionTime();
                if (lastTransitionTime != null) {
                    Date lastTransTime = DateSafeUtils.parse(lastTransitionTime, K8sConstants.TIME_PATTERN);
                    kcPodCondition.setLastTransitionTime(CommonUtils.timeOffset(lastTransTime));
                }
                kcPodConditions.add(kcPodCondition);
            }
        }
        kcPod.setKcPodConditions(kcPodConditions);
    }

    private static void setInitContainers(KcPod kcPod, Pod pod, PodMetricsList podMetrics) throws ParseException {
        List<KcPodContainer> kcPodContainers = new ArrayList<>();
        PodStatus status = pod.getStatus();
        List<ContainerStatus> containerStatuses = status.getInitContainerStatuses();
        List<Container> containers = pod.getSpec().getInitContainers();
        if (CollectionUtils.isNotEmpty(containerStatuses)) {
            for (ContainerStatus containerStatus : containerStatuses) {
                for (Container container : containers) {
                    if (Objects.equals(containerStatus.getName(), container.getName())) {
                        KcPodContainer kcPodContainer = getContainer(container, containerStatus, kcPod, podMetrics);
                        kcPodContainers.add(kcPodContainer);
                        break;
                    }
                }
            }
        }
        kcPod.setInitContainers(kcPodContainers);
    }

    private static void setContainers(KcPod kcPod, Pod pod, PodMetricsList podMetrics) throws ParseException {
        List<KcPodContainer> kcPodContainers = new ArrayList<>();
        PodStatus status = pod.getStatus();
        List<ContainerStatus> containerStatuses = status.getContainerStatuses();
        List<Container> containers = pod.getSpec().getContainers();
        if (CollectionUtils.isNotEmpty(containerStatuses)) {
            for (ContainerStatus containerStatus : containerStatuses) {
                for (Container container : containers) {
                    if (Objects.equals(containerStatus.getName(), container.getName())) {
                        KcPodContainer kcPodContainer = getContainer(container, containerStatus, kcPod, podMetrics);
                        kcPodContainers.add(kcPodContainer);
                        break;
                    }
                }
            }
        }
        kcPod.setContainers(kcPodContainers);

    }

    private static void setHealthParam(KcHealthParam healthParam, Probe probe) {
        healthParam.setHealthThreshold(probe.getSuccessThreshold())
                .setUnHealthThreshold(probe.getFailureThreshold())
                .setInitialDelaySeconds(probe.getInitialDelaySeconds())
                .setPeriodSeconds(probe.getPeriodSeconds())
                .setTimeout(probe.getTimeoutSeconds());
    }

    public static <T> T getHealth(Class<T> tClass, Probe probe) {
        if (probe != null) {
            KcProbe kcProbe = null;
            if (tClass == KcLivenessProbe.class) {
                kcProbe = new KcLivenessProbe();
            } else {
                kcProbe = new KcReadinessProbe();
            }
            TCPSocketAction tcpSocket = probe.getTcpSocket();
            ExecAction exec = probe.getExec();
            HTTPGetAction httpGet = probe.getHttpGet();
            if (exec != null) {
                kcProbe.setType("COMMAND");
                KcHealthCommandParam healthParam = new KcHealthCommandParam();
                healthParam.setCommands(exec.getCommand());
                setHealthParam(healthParam, probe);
                kcProbe.setHealthCommandParam(healthParam);
            } else if (httpGet != null) {
                kcProbe.setType("HTTP");
                KcHealthHttpParam healthParam = new KcHealthHttpParam();
                List<HTTPHeader> httpHeaders = httpGet.getHttpHeaders();
                if (httpHeaders != null) {
                    Map<String, String> header = new HashMap<>();
                    for (HTTPHeader httpHeader : httpHeaders) {
                        header.put(httpHeader.getName(), httpHeader.getValue());
                    }
                    healthParam.setHeaders(header);
                }
                healthParam.setPath(httpGet.getPath())
                        .setPort(httpGet.getPort() == null ? null : httpGet.getPort().getIntVal())
                        .setHost(httpGet.getHost());
                setHealthParam(healthParam, probe);
                kcProbe.setHealthHttpParam(healthParam);
            } else if (tcpSocket != null) {
                kcProbe.setType("TCP");
                KcHealthTcpParam healthParam = new KcHealthTcpParam();
                healthParam.setPort(tcpSocket.getPort() == null ? null : tcpSocket.getPort().getIntVal());
                setHealthParam(healthParam, probe);
                kcProbe.setHealthTcpParam(healthParam);
            }
            return (T) kcProbe;
        }
        return null;
    }

    private static KcPodContainer getContainer(Container container, ContainerStatus containerStatus, KcPod kcPod, PodMetricsList podMetrics) throws ParseException {
        KcPodContainer podContainer = new KcPodContainer();
        podContainer.setImage(container.getImage())
                .setImagePullPolicy(container.getImagePullPolicy());

        Probe livenessProbe = container.getLivenessProbe();
        KcLivenessProbe kcLivenessProbe = getHealth(KcLivenessProbe.class, livenessProbe);

        Probe readinessProbe = container.getReadinessProbe();
        KcReadinessProbe kcReadinessProbe = getHealth(KcReadinessProbe.class, readinessProbe);

        podContainer.setLivenessProbe(kcLivenessProbe)
                .setReadinessProbe(kcReadinessProbe)
                .setName(container.getName());

        List<ContainerPort> ports = container.getPorts();
        if (CollectionUtils.isNotEmpty(ports)) {
            List<KcPodContainerPort> kcPodContainerPorts = new ArrayList<>();
            for (ContainerPort port : ports) {
                KcPodContainerPort kcPodContainerPort = new KcPodContainerPort();
                kcPodContainerPort.setContainerPort(port.getContainerPort())
                        .setHostIp(port.getHostIP())
                        .setHostPort(port.getHostPort() == null ? null : port.getHostPort().toString())
                        .setProtocol(port.getProtocol());
                kcPodContainerPorts.add(kcPodContainerPort);
            }
        }

        podContainer.setTerminationMessagePath(container.getTerminationMessagePath())
                .setTerminationMessagePolicy(container.getTerminationMessagePolicy());
        //    /**
        //     * 存储 TODO
        //     * */
        //    private String volumeMounts;
        //
        //    /**
        //     * 存储 TODO
        //     * */
        //    private String volumeDevices;
        podContainer.setContainerId(containerStatus.getContainerID())
                .setImageId(podContainer.getImageId()).setReady(containerStatus.getReady())
                .setRestartCount(containerStatus.getRestartCount());
        podContainer.setLastStateRunning(getRunning(containerStatus.getLastState().getRunning()))
                .setStateRunning(getRunning(containerStatus.getState().getRunning()))
                .setLastStateTerminated(getTerminated(containerStatus.getLastState().getTerminated()))
                .setStateTerminated(getTerminated(containerStatus.getState().getTerminated()))
                .setLastStateWaiting(getWaiting(containerStatus.getLastState().getWaiting()))
                .setStateWaiting(getWaiting(containerStatus.getState().getWaiting()));

        //resource
        podContainer.setResource(setResource(container));

        //环境变量
        podContainer.setEnvs(setEnvs(container));

        //挂载点
        podContainer.setVolumeMounts(setVolumeMounts(container));

        if (podMetrics != null) {
            List<PodMetrics> items = podMetrics.getItems();
            if (items != null) {
                Iterator<PodMetrics> iterator = items.iterator();
                while (iterator.hasNext()) {
                    PodMetrics item = iterator.next();
                    if (item != null) {
                        ObjectMeta metadata = item.getMetadata();
                        String name1 = metadata.getName();
                        if (Objects.equals(name1, kcPod.getBaseInfo().getName())) {
                            long usedMemory = 0L;
                            double usedCpu = 0.0;
                            double usedStorage = 0;
                            List<ContainerMetrics> containers = item.getContainers();
                            if (containers != null) {
                                Iterator<ContainerMetrics> iterator1 = containers.iterator();
                                while (iterator1.hasNext()) {
                                    ContainerMetrics cm = iterator1.next();
                                    if (cm != null) {
                                        Map<String, Quantity> usage = cm.getUsage();
                                        String connStr = cm.getName();
                                        if (usage != null && Objects.equals(connStr, podContainer.getName())) {
                                            Quantity memoryStr = usage.get("memory");
                                            Quantity cpuStr = usage.get("cpu");
                                            if (memoryStr != null) {
                                                usedMemory += Quantity.getAmountInBytes(memoryStr).longValue() / 1024;
                                            }
                                            if (cpuStr != null) {
                                                usedCpu += Quantity.getAmountInBytes(cpuStr).doubleValue();
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            podContainer.setUsedCpu(usedCpu)
                                    .setUsedMemory(usedMemory);
                        }
                    }
                }
            }
        }

        return podContainer;
    }

    //获取挂载点
    public static List<KcPodContainerVoMount> setVolumeMounts(Container container) {
        List<KcPodContainerVoMount> podContainerVoMounts = new ArrayList<>();
        List<VolumeMount> volumeMounts = container.getVolumeMounts();
        if (CollectionUtils.isNotEmpty(volumeMounts)) {
            for (VolumeMount volumeMount : volumeMounts) {
                KcPodContainerVoMount kcPodContainerVoMount = new KcPodContainerVoMount();
                kcPodContainerVoMount.setMountPath(volumeMount.getMountPath())
                        .setName(volumeMount.getName())
                        .setReadOnly(volumeMount.getReadOnly() == null ? false : volumeMount.getReadOnly())
                        .setSubPath(volumeMount.getSubPath())
                        .setSubPathExpr(volumeMount.getSubPathExpr());
                podContainerVoMounts.add(kcPodContainerVoMount);
            }
        }
        return podContainerVoMounts;
    }

    public static LinkedHashMap<String, String> setEnvs(Container container) {
        List<EnvVar> envs = container.getEnv();
        if (CollectionUtils.isNotEmpty(envs)) {
            LinkedHashMap<String, String> kcEnvs = new LinkedHashMap<>();
            for (EnvVar env : envs) {
                String name = env.getName();
                String value = env.getValue();
                kcEnvs.put(name, value);
            }
            return kcEnvs;
        }
        return null;
    }

    public static KcResource setResource(Container container) {
        KcResource kcResource = new KcResource();
        if (container.getResources() != null) {
            ResourceRequirements resources = container.getResources();
            Map<String, Quantity> requests = resources.getRequests();
            Map<String, Quantity> limits = resources.getLimits();
            if (MapUtils.isNotEmpty(requests)) {
                Quantity cpu = requests.get("cpu");
                Quantity memory = requests.get("memory");
                if (cpu != null) {
                    kcResource.setCpuMin((int) Math.round((Quantity.getAmountInBytes(cpu)).doubleValue() * 1000));
                }
                if (memory != null) {
                    kcResource.setMemoryMin(((Double)((Quantity.getAmountInBytes(memory)).doubleValue() / 1024 / 1024)).intValue());
                }
            }
            if (MapUtils.isNotEmpty(limits)) {
                Quantity cpu = limits.get("cpu");
                Quantity memory = limits.get("memory");
                if (cpu != null) {
                    kcResource.setCpuMax((int) Math.round((Quantity.getAmountInBytes(cpu)).doubleValue() * 1000));
                }
                if (memory != null) {
                    kcResource.setMemoryMax(((Double)((Quantity.getAmountInBytes(memory)).doubleValue() / 1024 / 1024)).intValue());
                }
            }
        }
        return kcResource;
    }

    private static KcPodContainerStateWaiting getWaiting(ContainerStateWaiting containerStateWaiting) throws ParseException {
        if (containerStateWaiting != null) {
            KcPodContainerStateWaiting kcPodContainerStateWaiting = new KcPodContainerStateWaiting();
            kcPodContainerStateWaiting.setMessage(containerStateWaiting.getMessage())
                    .setReason(containerStateWaiting.getReason());
            return kcPodContainerStateWaiting;
        }
        return null;
    }

    private static KcPodContainerStateRunning getRunning(ContainerStateRunning containerStateRunning) throws ParseException {
        if (containerStateRunning != null) {
            String startedAt = containerStateRunning.getStartedAt();
            Date date = DateSafeUtils.parse(startedAt, K8sConstants.TIME_PATTERN);
            date = CommonUtils.timeOffset(date);
            KcPodContainerStateRunning kcPodContainerStateRunning = new KcPodContainerStateRunning();
            kcPodContainerStateRunning.setStartedAt(date);
            return kcPodContainerStateRunning;
        }
        return null;
    }

    private static KcPodContainerStateTerminated getTerminated(ContainerStateTerminated containerStateTerminated) throws ParseException {
        if (containerStateTerminated != null) {
            KcPodContainerStateTerminated kcPodContainerStateTerminated = new KcPodContainerStateTerminated();
            kcPodContainerStateTerminated.setExitCode(containerStateTerminated.getExitCode())
                    .setFinishedAt(CommonUtils.timeOffset(DateSafeUtils.parse(containerStateTerminated.getFinishedAt(), K8sConstants.TIME_PATTERN)))
                    .setMessage(containerStateTerminated.getMessage())
                    .setReason(containerStateTerminated.getReason())
                    .setSignal(containerStateTerminated.getSignal())
                    .setStartedAt(CommonUtils.timeOffset(DateSafeUtils.parse(containerStateTerminated.getStartedAt(), K8sConstants.TIME_PATTERN)));
            return kcPodContainerStateTerminated;
        }
        return null;
    }


    private static void setBaseInfo(KcPod kcPod, Pod pod, PodMetricsList podMetricsList) throws ParseException {
        KcPodBaseInfo podBaseInfo = new KcPodBaseInfo();
        ObjectMeta metadata = pod.getMetadata();
        PodSpec spec = pod.getSpec();
        PodStatus status = pod.getStatus();
        podBaseInfo.setApiVersion(pod.getApiVersion())
                .setName(metadata.getName());
        Date createTime = DateSafeUtils.parse(metadata.getCreationTimestamp(), K8sConstants.TIME_PATTERN);
        createTime = CommonUtils.timeOffset(createTime);
        podBaseInfo.setCreateTime(createTime).setRunTimeStr(CommonUtils.getTimeDistance(createTime))
                .setNamespace(metadata.getNamespace());
        Map<String, String> labels = metadata.getLabels();
        if (MapUtils.isNotEmpty(labels)) {
            for (Map.Entry<String, String> entry : labels.entrySet()) {
                String[] label = new String[2];
                label[0] = entry.getKey();
                label[1] = entry.getValue();
                podBaseInfo.getLabels().add(label);
            }
        }
        podBaseInfo.setDnsPolicy(spec.getDnsPolicy())
                .setEnableServiceLinks(spec.getEnableServiceLinks())
                .setNodeName(spec.getNodeName())
                .setRestartPolicy(spec.getRestartPolicy())
                .setServiceAccount(spec.getServiceAccountName())
                .setTerminationGracePeriodSeconds(spec.getTerminationGracePeriodSeconds())
                .setHostIp(status.getHostIP())
                .setPhase(status.getPhase())
                .setPodIp(status.getPodIP())
                .setQosClass(status.getQosClass())
                .setStartTime(CommonUtils.timeOffset(DateSafeUtils.parse(status.getStartTime(), K8sConstants.TIME_PATTERN)));
        setPodMetricsInfo(podBaseInfo, podMetricsList);

        kcPod.setBaseInfo(podBaseInfo);
    }

    private static void setPodMetricsInfo(KcPodBaseInfo baseInfo, PodMetricsList podMetrics) {
        if (podMetrics == null) return;
        String name = baseInfo.getName();
        List<PodMetrics> items = podMetrics.getItems();
        if (items != null) {
            Iterator<PodMetrics> iterator = items.iterator();
            while (iterator.hasNext()) {
                PodMetrics item = iterator.next();
                if (item != null) {
                    ObjectMeta metadata = item.getMetadata();
                    String name1 = metadata.getName();
                    if (Objects.equals(name1, name)) {
                        long usedMemory = 0L;
                        double usedCpu = 0.0;
                        double usedStorage = 0;
                        List<ContainerMetrics> containers = item.getContainers();
                        if (containers != null) {
                            Iterator<ContainerMetrics> iterator1 = containers.iterator();
                            while (iterator1.hasNext()) {
                                ContainerMetrics container = iterator1.next();
                                if (container != null) {
                                    Map<String, Quantity> usage = container.getUsage();
                                    if (usage != null) {
                                        Quantity memoryStr = usage.get("memory");
                                        Quantity cpuStr = usage.get("cpu");
                                        if (memoryStr != null) {
                                            usedMemory += Quantity.getAmountInBytes(memoryStr).longValue() / 1024;
                                        }
                                        if (cpuStr != null) {
                                            usedCpu += Quantity.getAmountInBytes(cpuStr).doubleValue();
                                        }
                                    }
                                }
                            }
                        }
                        baseInfo.setUsedCpu(usedCpu)
                                .setUsedMemory(usedMemory);
                        return;
                    }
                }
            }
        }
    }

//    private static void setPodMetricsInfo(KcPodBaseInfo baseInfo, PodMetricsList podMetrics) {
//        if (podMetrics == null) return;
//        String name = baseInfo.getName();
//        JSONArray items = podMetrics.getJSONArray("items");
//        if (items != null) {
//            Iterator<Object> iterator = items.stream().iterator();
//            while (iterator.hasNext()) {
//                JSONObject item = (JSONObject) iterator.next();
//                if (item != null) {
//                    JSONObject metadata = item.getJSONObject("metadata");
//                    String name1 = metadata.getStr("name");
//                    if (Objects.equals(name1, name)) {
//                        long usedMemory = 0L;
//                        double usedCpu = 0.0;
//                        double usedStorage = 0;
//                        JSONArray containers = item.getJSONArray("containers");
//                        if (containers != null) {
//                            Iterator<Object> iterator1 = containers.stream().iterator();
//                            while (iterator1.hasNext()) {
//                                JSONObject container = (JSONObject) iterator1.next();
//                                if (container != null) {
//                                    JSONObject usage = container.getJSONObject("usage");
//                                    if (usage != null) {
//                                        String memoryStr = usage.getStr("memory");
//                                        String cpuStr = usage.getStr("cpu");
//                                        if (memoryStr.contains("Ki")) {
//                                            usedMemory += Long.valueOf(StringUtils.substringBeforeLast(memoryStr, "Ki"));
//                                        }
//                                        if (cpuStr.contains("n")) {
//                                            usedCpu += Double.valueOf(StringUtils.substringBeforeLast(cpuStr, "n")) / 1000 / 1000 / 1000;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        baseInfo.setUsedCpu(usedCpu)
//                                .setUsedMemory(usedMemory);
//                        return;
//                    }
//                }
//            }
//        }
//    }

}
