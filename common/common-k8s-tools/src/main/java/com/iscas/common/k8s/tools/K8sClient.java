package com.iscas.common.k8s.tools;

import com.iscas.common.k8s.tools.cfg.K8sConfig;
import com.iscas.common.k8s.tools.exception.K8sCleintRuntimeException;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * kubernetes-client github地址 https://github.com/fabric8io/kubernetes-client
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/2 13:49
 * @since jdk1.8
 */
public class K8sClient {
    private K8sClient() {
    }
    private volatile static KubernetesClient kc = null;
    private static K8sConfig defaultK8sConfig = null;

    public static void setDefaultConfig(K8sConfig k8sConfig) {
        defaultK8sConfig = k8sConfig;
    }

    public static KubernetesClient getInstance() {
        if (defaultK8sConfig == null) {
            throw new K8sCleintRuntimeException("k8s客户端配置为空");
        }
        return getInstance(defaultK8sConfig);
    }

//    public static KubernetesClient getInstance(K8sConfig k8sConfig) {
//        if (kc == null) {
//            synchronized (K8sClient.class) {
//                if (kc == null) {
//                    System.setProperty("kubernetes.certs.ca.file", k8sConfig.getCaPath());
//                    Config config = new ConfigBuilder()
//                            //這是k8s集群訪問的TOKEN
//                            .withOauthToken(k8sConfig.getToken())
////                .withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRlZmF1bHQtdG9rZW4tNWZrMjQiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGVmYXVsdCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjNjOWVlMjI2LWE2YTQtNGE0Yi05YjdmLWVhODRiMzMwNGY4MiIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpkZWZhdWx0OmRlZmF1bHQifQ.FGCvmtFrwahz2gLwRBT_rAFYccMZwn-uG7pRXxnCq_XyJ9ZcmS68JMzDLj3PqxY6s4UBLZenwTy0JjhQuhEKsKVLnqDYxc1nXbvCSFzisDVNpW732c8KqtNgqIH2DHXGYB67TcNxVr4dgY0H75k0_CW8p0hXjPW6tNhaVNg_HXR8kJDmKQ-jyYCZeAIVAy-FtcXmduMt5qMjt9F66mMn2YFrdyQyiUp46PLbvJegh2H9_o-RAim8O8anWnTYcSr_T8DBhnxuXD69fdvS0rGM8qK4woxp8tMyj_ixkj3z0K1nd4HOhZQNYulIsSlJz8AW1Wu52CqAaEDbPRcifFNknQ")
//                            //固定用v1就行
//                            .withApiVersion(k8sConfig.getApiVersion())
//                            //這是k8s的master節點地址，端口如果沒改應該也是6443
//                            .withMasterUrl(k8sConfig.getApiServerPath()).build();
//                    kc = new DefaultKubernetesClient(config);
//                }
//            }
//        }
//        return kc;
//    }


    /**
     * 暂时使用每次创建一个kubernetes-client的方式，使用单例模式，发现程序执行完线程不会退出
     * */
    public static KubernetesClient getInstance(K8sConfig k8sConfig) {

        synchronized (K8sClient.class) {
            System.setProperty("kubernetes.certs.ca.file", k8sConfig.getCaPath());
            Config config = new ConfigBuilder()
                    //這是k8s集群訪問的TOKEN
                    .withOauthToken(k8sConfig.getToken())
//                .withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRlZmF1bHQtdG9rZW4tNWZrMjQiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGVmYXVsdCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjNjOWVlMjI2LWE2YTQtNGE0Yi05YjdmLWVhODRiMzMwNGY4MiIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpkZWZhdWx0OmRlZmF1bHQifQ.FGCvmtFrwahz2gLwRBT_rAFYccMZwn-uG7pRXxnCq_XyJ9ZcmS68JMzDLj3PqxY6s4UBLZenwTy0JjhQuhEKsKVLnqDYxc1nXbvCSFzisDVNpW732c8KqtNgqIH2DHXGYB67TcNxVr4dgY0H75k0_CW8p0hXjPW6tNhaVNg_HXR8kJDmKQ-jyYCZeAIVAy-FtcXmduMt5qMjt9F66mMn2YFrdyQyiUp46PLbvJegh2H9_o-RAim8O8anWnTYcSr_T8DBhnxuXD69fdvS0rGM8qK4woxp8tMyj_ixkj3z0K1nd4HOhZQNYulIsSlJz8AW1Wu52CqAaEDbPRcifFNknQ")
                    //固定用v1就行
                    .withApiVersion(k8sConfig.getApiVersion())
                    //這是k8s的master節點地址，端口如果沒改應該也是6443
                    .withMasterUrl(k8sConfig.getApiServerPath()).build();
            KubernetesClient kc = new DefaultKubernetesClient(config);
            return kc;
        }

    }
}
