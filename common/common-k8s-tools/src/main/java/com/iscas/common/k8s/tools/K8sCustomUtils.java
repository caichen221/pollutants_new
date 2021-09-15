package com.iscas.common.k8s.tools;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * K8S自定义访问请求<br/>
 * 从KubernetesClient中获取OkHttpClient，然后访问集群
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/23 14:50
 * @since jdk1.8
 */
public class K8sCustomUtils {
    private K8sCustomUtils() {};

    /**
     * GET请求
     * */
    public static String doGet(String url) throws IOException {
        KubernetesClient kc = K8sClient.getInstance();
        OkHttpClient httpClient = ((DefaultKubernetesClient) kc).getHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        Call call = httpClient.newCall(request);
        String s = call.execute().body().string();
        return s;
    }

    /**
     * GET请求
     * */
    public static String doPost(String url) throws IOException {
        KubernetesClient kc = K8sClient.getInstance();
        OkHttpClient httpClient = ((DefaultKubernetesClient) kc).getHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        Call call = httpClient.newCall(request);
        String s = call.execute().body().string();
        return s;
    }
}
