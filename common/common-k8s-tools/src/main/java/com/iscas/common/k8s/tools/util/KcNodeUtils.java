package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.exception.K8sClientException;
import com.iscas.common.k8s.tools.model.node.*;
import com.iscas.common.tools.core.date.DateSafeUtils;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import lombok.Cleanup;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 * 节点信息操作工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/2 16:04
 * @since jdk1.8
 */
public class KcNodeUtils {
    private KcNodeUtils() {}

    private static KcNode getOneNode(Node node, KubernetesClient kc) throws K8sClientException {
        KcNode kcNode = new KcNode();

        String name = null;
        String apiVersion = "v1";
        String address = null;
        String status = null;
        String runTimeStr = null;
        Date createTime = null;

        //注入值
        apiVersion = node.getApiVersion();
        ObjectMeta metadata = node.getMetadata();
        if (metadata != null) {
            name = metadata.getName();
            String creationTimestamp = metadata.getCreationTimestamp();
            try {
                createTime = DateSafeUtils.parse(creationTimestamp, "yyyy-MM-dd'T'HH:mm:ss'Z'");
            } catch (ParseException e) {
                throw new K8sClientException("创建时间类型转换出错", e);
            }
            runTimeStr = CommonUtils.getTimeDistance(createTime);
        }
        NodeStatus nodeStatus = node.getStatus();
        if (nodeStatus != null) {
            List<NodeAddress> addresses = nodeStatus.getAddresses();
            if (CollectionUtils.isNotEmpty(addresses)) {
                address = addresses.get(0).getAddress();
            }
            status = nodeStatus.getPhase();
            if (StringUtils.isEmpty(status)) {
                status = "ready";
            }
        }

        //获取基本信息
        setNodeBaseInfo(kcNode, node);

        //获取容器组信息
        PodList podList = kc.pods().list();
        setNodePods(kcNode, podList, name);

        //获取镜像信息
        setNodeImages(kcNode, node);

        kcNode.setApiVersion(apiVersion)
                .setAddress(address)
                .setName(name)
                .setRunTimeStr(runTimeStr)
                .setStatus(status);

        return kcNode;
    }

    /**
     * 获取节点的镜像信息
     * */
    private static void setNodeImages(KcNode kcNode, Node node) {
        NodeStatus nodeStatus = node.getStatus();
        if (nodeStatus != null) {
            List<ContainerImage> containerImages = nodeStatus.getImages();
            if (CollectionUtils.isNotEmpty(containerImages)) {
                List<KcNodeImage> images = new ArrayList<>();
                for (ContainerImage containerImage : containerImages) {
                    //镜像名字
                    List<String> names = null;
                    //大小
                    Long size = null;
                    KcNodeImage nodeImage = new KcNodeImage();

                    names = containerImage.getNames();
                    size = containerImage.getSizeBytes();

                    nodeImage.setNames(names)
                            .setSize(size);
                    images.add(nodeImage);
                }
                kcNode.setImages(images);
            }
        }
    }

    /**
     * 获取容器组信息
     * */
    private static void setNodePods(KcNode kcNode, PodList podList, String nodeName) throws K8sClientException {
        if (podList != null) {
            List<Pod> podItems = podList.getItems();
            if (CollectionUtils.isNotEmpty(podItems)) {
                for (Pod podItem : podItems) {
                    PodSpec spec = podItem.getSpec();
                    if (spec != null) {
                        if (Objects.equals(spec.getNodeName(), nodeName)) {
                            //此pod为当前节点的，获取其他属性
                            List<KcNodePod> pods = kcNode.getPods();
                            if (pods == null) {
                                pods = new ArrayList<>();
                                kcNode.setPods(pods);
                            }

                            //命名空间
                            String namespace = null;
                            //名称
                            String name = null;
                            //状态
                            String status = null;
                            //容器组IP
                            String innerIp = null;
                            //创建时间
                            Date createTime = null;
                            //运行时间
                            String runTimeStr = null;

                            ObjectMeta metadata = podItem.getMetadata();
                            if (metadata != null) {
                                //命名空间
                                namespace = metadata.getNamespace();
                                //名称
                                name = metadata.getName();
                            }

                            PodStatus podStatus = podItem.getStatus();
                            if (podStatus != null) {
                                //状态
                                status = podStatus.getPhase();
                                //内部IP
                                innerIp = podStatus.getPodIP();

                                String startTime = podStatus.getStartTime();
                                try {
                                    createTime = DateSafeUtils.parse(startTime, "yyyy-MM-dd'T'HH:mm:ss'Z'");
                                    runTimeStr = CommonUtils.getTimeDistance(createTime);
                                } catch (Exception e) {
                                    throw new K8sClientException("创建时间类型转换出错", e);
                                }
                            }

                            KcNodePod nodePod = new KcNodePod();
                            nodePod.setCreateTime(createTime)
                                    .setInnerIp(innerIp)
                                    .setName(name)
                                    .setNamespace(namespace)
                                    .setRunTimeStr(runTimeStr)
                                    .setStatus(status);
                            pods.add(nodePod);
                        }
                    }
                }
            }
        }
    }

    /**
     * 为节点设置基本信息
     * */
    private static void setNodeBaseInfo(KcNode kcNode, Node node) throws K8sClientException {
        KcNodeBaseInfo baseInfo = new KcNodeBaseInfo();

        String name = null;
        Date createTime = null;
        String runTimeStr = null;
        List<String[]> labels = null;
        List<String[]> annotations = null;
        List<KcNodeBaseInfoTaint> taints = null;
        String cpu = null;
        String transientStorage = null;
        Integer maxPods = null;
        String cidr = null;

        //todo 未找到方式获取ipTunnelAddr
        String ipTunnelAddr = null;
        String internalIP = null;
        String hostname = null;
        String os = null;
        String architecture = null;
        String osImage = null;
        String osKernel = null;
        String containerEngine = null;
        String kubeletVersion = null;
        String kubeProxyVersion = null;

        name = kcNode.getName();
        runTimeStr = kcNode.getRunTimeStr();

        ObjectMeta metadata = node.getMetadata();
        if (metadata != null) {
            name = metadata.getName();
            String creationTimestamp = metadata.getCreationTimestamp();
            try {
                createTime = DateSafeUtils.parse(creationTimestamp, "yyyy-MM-dd'T'HH:mm:ss'Z'");
            } catch (ParseException e) {
                throw new K8sClientException("创建时间类型转换出错", e);
            }
            runTimeStr = CommonUtils.getTimeDistance(createTime);

            //获取labels
            Map<String, String> metaLabels = metadata.getLabels();
            if (MapUtils.isNotEmpty(metaLabels)) {
                labels = new ArrayList<>();
                for (Map.Entry<String, String> entry: metaLabels.entrySet()) {
                    String[] labelArray = new String[2];
                    labelArray[0] = entry.getKey();
                    labelArray[1] = entry.getValue();
                    labels.add(labelArray);
                }
            }

            //获取annotations
            Map<String, String> metaAnnotations = metadata.getAnnotations();
            if (MapUtils.isNotEmpty(metaAnnotations)) {
                annotations = new ArrayList<>();
                for (Map.Entry<String, String> entry: metaAnnotations.entrySet()) {
                    String[] annotationArray = new String[2];
                    annotationArray[0] = entry.getKey();
                    annotationArray[1] = entry.getValue();
                    annotations.add(annotationArray);
                }
            }



        }

        NodeSpec spec = node.getSpec();
        if (spec != null) {
            //获取taints
            List<Taint> specTaints = spec.getTaints();
            if (CollectionUtils.isNotEmpty(specTaints)) {
                taints = new ArrayList<>();
                for (Taint specTaint : specTaints) {
                    KcNodeBaseInfoTaint kcTaint = new KcNodeBaseInfoTaint();
                    kcTaint.setKey(specTaint.getKey());
                    kcTaint.setValue(specTaint.getValue());
                    kcTaint.setEffect(specTaint.getEffect());
                    taints.add(kcTaint);
                }
            }

            //获取cidr
            cidr = spec.getPodCIDR();

        }

        NodeStatus nodeStatus = node.getStatus();
        if (nodeStatus != null) {
            Map<String, Quantity> capacity = nodeStatus.getCapacity();
            if (capacity != null) {
                //获取cpu
                Quantity cpuQuantity = capacity.get("cpu");
                if (cpuQuantity != null) {
                    cpu = cpuQuantity.getAmount();
                }

                //获取transientStorage
                Quantity storageQuantity = capacity.get("ephemeral-storage");
                if (storageQuantity != null) {
                    cpu = storageQuantity.getAmount();
                }

                //获取maxPods
                Quantity podsQuantity = capacity.get("pods");
                if (podsQuantity != null) {
                    maxPods = Integer.parseInt(podsQuantity.getAmount());
                }

            }

            List<NodeAddress> addresses = nodeStatus.getAddresses();
            if (CollectionUtils.isNotEmpty(addresses)) {
                for (NodeAddress address : addresses) {
                    String type = address.getType();
                    if (Objects.equals("InternalIP", type)) {
                        //获取internalIP
                        String addr = address.getAddress();
                        internalIP = addr;
                    } else if (Objects.equals("Hostname", type)) {
                        //获取hostname
                        String addr = address.getAddress();
                        hostname = addr;
                    }
                }
            }

            NodeSystemInfo nodeInfo = nodeStatus.getNodeInfo();
            if (nodeInfo != null) {
                //获取os
                os = nodeInfo.getOperatingSystem();
                //获取architecture
                architecture = nodeInfo.getArchitecture();
                //获取osImage
                osImage = nodeInfo.getOsImage();
                //获取osKernel
                osKernel = nodeInfo.getKernelVersion();
                //获取containerEngine
                containerEngine = nodeInfo.getContainerRuntimeVersion();
                //获取kubeletVersion
                kubeletVersion = nodeInfo.getKubeletVersion();
                //获取kubeProxyVersion
                kubeProxyVersion = nodeInfo.getKubeProxyVersion();
            }

        }

        baseInfo.setName(name)
                .setCreateTime(createTime)
                .setRunTimeStr(runTimeStr)
                .setLabels(labels)
                .setAnnotations(annotations)
                .setTaints(taints)
                .setCpu(cpu)
                .setTransientStorage(transientStorage)
                .setMaxPods(maxPods)
                .setCidr(cidr)
                .setIpTunnelAddr(ipTunnelAddr)
                .setInternalIP(internalIP)
                .setHostname(hostname)
                .setOs(os)
                .setArchitecture(architecture)
                .setOsImage(osImage)
                .setOsKernel(osKernel)
                .setContainerEngine(containerEngine)
                .setKubeletVersion(kubeletVersion)
                .setKubeProxyVersion(kubeProxyVersion);

        //注入condition
        setNodeBaseInfoCondition(baseInfo, node);

        kcNode.setBaseInfo(baseInfo);
    }

    private static void setNodeBaseInfoCondition(KcNodeBaseInfo baseInfo, Node node) throws K8sClientException {
        NodeStatus nodeStatus = node.getStatus();
        if (nodeStatus != null) {
            List<NodeCondition> conditions = nodeStatus.getConditions();
            if (CollectionUtils.isNotEmpty(conditions)) {
                List<KcNodeRuntimeInfo> kcConditions = new ArrayList<>();
                for (NodeCondition condition : conditions) {
                    KcNodeRuntimeInfo kcCondtion = new KcNodeRuntimeInfo();

                    String type = null;
                    String status = null;
                    Date lastHeartbeatTime = null;
                    Date lastTransitionTime = null;
                    String reason = null;
                    String message = null;

                    type = condition.getType();
                    status = condition.getStatus();
                    try {
                        lastHeartbeatTime = DateSafeUtils.parse(condition.getLastHeartbeatTime(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
                        lastTransitionTime = DateSafeUtils.parse(condition.getLastTransitionTime(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
                    } catch (ParseException e) {
                        throw new K8sClientException("时间类型转换出错", e);
                    }
                    reason = condition.getReason();
                    message = condition.getMessage();
                    kcCondtion.setType(type)
                            .setLastHeartbeatTime(lastHeartbeatTime)
                            .setLastTransitionTime(lastTransitionTime)
                            .setMessage(message)
                            .setReason(reason)
                            .setStatus(status);
                    kcConditions.add(kcCondtion);
                }
                baseInfo.setConditions(kcConditions);
            }
        }
    }

    /**
     * 查看某个节点存在如否
     * */
    public static boolean checkNodeExsits(String nodeName) {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            return false;
        }

        Node node = resource.get();
        if (node == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取节点信息
     * */
    public static List<KcNode> getNodes() throws K8sClientException {
        List<KcNode> kcNodes = null;
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kc.nodes();
        if (nodes != null) {
            NodeList nodeList = nodes.list();
            if (nodeList != null) {
                List<Node> items = nodeList.getItems();
                if (CollectionUtils.isNotEmpty(items)) {
                    kcNodes = new ArrayList<>();
                    for (Node item : items) {
                        KcNode kcNode = getOneNode(item, kc);
                        kcNodes.add(kcNode);
                    }
                }
            }
        }
        return kcNodes;
    }

    /**
     * 编辑污点
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param kcTaint 编辑的污点
     * @throws
     * @return void
     */
    public static void editTaint(String nodeName, KcNodeBaseInfoTaint kcTaint) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }
        int index = -1;
        NodeSpec spec = node.getSpec();
        if (spec == null) {
            throw new K8sClientException(String.format("未找到对应的污点\"%s\",无法编辑", kcTaint.getKey()));
        }
        List<Taint> taints = spec.getTaints();
        if (CollectionUtils.isNotEmpty(taints)) {
            for (int i = 0; i < taints.size(); i++) {
                if (Objects.equals(taints.get(i).getKey(), kcTaint.getKey())) {
                    index = i;
                    break;
                }
            }
        }
        if (index == -1) {
            throw new K8sClientException(String.format("未找到对应的污点\"%s\",无法编辑", kcTaint.getKey()));
        }
        resource.edit().editSpec()
                .editTaint(index)
                .withKey(kcTaint.getKey())
                .withNewEffect(kcTaint.getEffect())
                //todo 不知道为啥为Null会报错，为Null时将它置为""
                .withNewValue(kcTaint.getValue() == null ? "" : kcTaint.getValue())
                .endTaint()
                .endSpec()
                .done();

    }


    /**
     * 新增污点
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param kcTaint 新增的污点
     * @throws
     * @return void
     */
    public static void addTaint(String nodeName, KcNodeBaseInfoTaint kcTaint) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        NodeSpec spec = node.getSpec();
        if (spec == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        int index = -1;
        List<Taint> taints = spec.getTaints();
        if (CollectionUtils.isNotEmpty(taints)) {
            for (int i = 0; i < taints.size(); i++) {
                if (Objects.equals(taints.get(i).getKey(), kcTaint.getKey())) {
                    index = i;
                    break;
                }
            }
        }
        if (index != -1) {
            throw new K8sClientException(String.format("找到对应的污点\"%s\",不可再新增同样的污点", kcTaint.getKey()));
        }
        resource.edit().editSpec()
                .addNewTaint()
                .withKey(kcTaint.getKey())
                .withNewEffect(kcTaint.getEffect())
                //todo 不知道为啥为Null会报错，为Null时将它置为""
                .withNewValue(kcTaint.getValue() == null ? "" : kcTaint.getValue())
                .endTaint()
                .endSpec()
                .done();

    }

    /**
     * 新增污点
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param taintKey 污点的key
     * @throws
     * @return void
     */
    public static void deleteTaint(String nodeName, String taintKey) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }
        Taint taint = null;
        NodeSpec spec = node.getSpec();
        if (spec == null) {
            throw new K8sClientException(String.format("未找到对应的spec"));
        }
        List<Taint> taints = spec.getTaints();
        if (CollectionUtils.isNotEmpty(taints)) {
            for (int i = taints.size() - 1; i >= 0; i--) {
                if (Objects.equals(taints.get(i).getKey(), taintKey)) {
                    taint = taints.remove(i);
                    break;
                }
            }
        }
        if (taint == null) {
            throw new K8sClientException(String.format("未找到对应的污点\"%s\",无需删除", taintKey));
        }
        resource.edit().editSpec()
                .withTaints(taints)
                .endSpec()
                .done();

    }

    /**
     * 新增label
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param label 新增的label
     * @throws
     * @return void
     */
    public static void addLabel(String nodeName, String[] label) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> labels = metadata.getLabels();
        if (MapUtils.isNotEmpty(labels)) {
            if (labels.containsKey(label[0])) {
                throw new K8sClientException(String.format("找到对应的label\"%s\",不可再新增同样的label", label[0]));
            }
        }

        resource.edit().editMetadata()
                .addToLabels(label[0], label[1])
                .endMetadata()
                .done();

    }


    /**
     * 编辑label
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param label 编辑的label
     * @throws
     * @return void
     */
    public static void editLabel(String nodeName, String[] label) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> labels = metadata.getLabels();
        if (MapUtils.isEmpty(labels)) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",不可编辑", label[0]));
        }
        if (!labels.containsKey(label[0])) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",不可编辑", label[0]));
        }

        resource.edit().editMetadata()
                .addToLabels(label[0], label[1])
                .endMetadata()
                .done();

    }

    /**
     * 删除label
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param labelKey label的key
     * @throws
     * @return void
     */
    public static void deleteLabel(String nodeName, String labelKey) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> labels = metadata.getLabels();
        if (MapUtils.isEmpty(labels)) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",无需删除", labelKey));
        }
        if (!labels.containsKey(labelKey)) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",无需删除", labelKey));
        }

        resource.edit().editMetadata()
                .removeFromLabels(labelKey)
                .endMetadata()
                .done();
    }

    /**
     * 新增annotations
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param annotation 新增的annotation
     * @throws
     * @return void
     */
    public static void addAnnotation(String nodeName, String[] annotation) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> annotationMap = metadata.getAnnotations();
        if (MapUtils.isNotEmpty(annotationMap)) {
            if (annotationMap.containsKey(annotation[0])) {
                throw new K8sClientException(String.format("找到对应的annotation\"%s\",不可再新增同样的annotation", annotation[0]));
            }
        }

        resource.edit().editMetadata()
                .addToAnnotations(annotation[0], annotation[1])
                .endMetadata()
                .done();

    }

    /**
     * 编辑annotation
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param annotations 编辑的annotations
     * @throws
     * @return void
     */
    public static void editAnnotation(String nodeName, String[] annotations) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> anno = metadata.getAnnotations();
        if (MapUtils.isEmpty(anno)) {
            throw new K8sClientException(String.format("找不到对应的annotation\"%s\",不可编辑", annotations[0]));
        }
        if (!anno.containsKey(annotations[0])) {
            throw new K8sClientException(String.format("找不到对应的annotation\"%s\",不可编辑", annotations[0]));
        }

        resource.edit().editMetadata()
                .addToAnnotations(annotations[0], annotations[1])
                .endMetadata()
                .done();

    }


    /**
     * 删除annotation
     * @version 1.0
     * @since jdk1.8
     * @date 2019/12/4
     * @param nodeName 节点名称
     * @param annotationKey annotation的key
     * @throws
     * @return void
     */
    public static void deleteAnnotation(String nodeName, String annotationKey) throws K8sClientException {
        @Cleanup KubernetesClient kcClient = K8sClient.getInstance();
        NonNamespaceOperation<Node, NodeList, DoneableNode, Resource<Node, DoneableNode>> nodes = kcClient.nodes();
        Resource<Node, DoneableNode> resource = nodes.withName(nodeName);
        if (resource == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        Node node = resource.get();
        if (node == null) {
            throw new K8sClientException(String.format("节点名称\"%s\"不存在", nodeName));
        }

        ObjectMeta metadata = node.getMetadata();
        if (metadata == null) {
            throw new K8sClientException(String.format("未找到对应节点的spec"));
        }
        Map<String, String> anno = metadata.getAnnotations();
        if (MapUtils.isEmpty(anno)) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",无需删除", annotationKey));
        }
        if (!anno.containsKey(annotationKey)) {
            throw new K8sClientException(String.format("找不到对应的label\"%s\",无需删除", annotationKey));
        }

        resource.edit().editMetadata()
                .removeFromAnnotations(annotationKey)
                .endMetadata()
                .done();
    }
}
