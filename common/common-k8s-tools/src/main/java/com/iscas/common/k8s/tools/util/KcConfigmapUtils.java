package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.Cleanup;

import java.util.Map;

/**
 * configmap相关操作
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/10 8:43
 * @since jdk1.8
 */
public class KcConfigmapUtils {
    private KcConfigmapUtils() {}

    public static Map<String, String> getConfigMapData(String namespace, String name) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
        ConfigMap configMap = kc.configMaps().inNamespace(namespace)
                .withName(name).get();
        return configMap.getData();
    }

}
