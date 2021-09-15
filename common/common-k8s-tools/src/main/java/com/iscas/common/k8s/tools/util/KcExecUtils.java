package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.cfg.K8sConfig;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ContainerResource;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.dsl.LogWatch;
import io.fabric8.kubernetes.client.dsl.internal.LogWatchCallback;
import lombok.Cleanup;
import okhttp3.*;
import okio.ByteString;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * 命令处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/14 15:55
 * @since jdk1.8
 */
public class KcExecUtils {
    private KcExecUtils() {}

    public static final int LOG_WATCH_LAST_LINES = 1000;


    public static LogWatch traceLog(String namespace, String podName, String containerName, int tailingLines) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        ContainerResource<String, LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream> containerResource = kc.pods()
//                .inNamespace(namespace)
//                .withName(podName)
//                .inContainer(containerName);
        ContainerResource<LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream, Boolean> containerResource = kc.pods().inNamespace(namespace).withName(podName).inContainer(podName);
        LogWatch logWatch1 = containerResource.tailingLines(tailingLines).watchLog();
        LogWatchCallback logWatchCallback = (LogWatchCallback) logWatch1;
        logWatchCallback.waitUntilReady();

//        LogWatch logWatch = containerResource/*.withPrettyOutput()*/.watchLog();
        return logWatch1;
    }

//    public static LogWatch traceLog(String namespace, String podName, String containerName) {
//        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        ContainerResource<String, LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream> containerResource = kc.pods()
//                .inNamespace(namespace)
//                .withName(podName)
//                .inContainer(containerName);
//        PrettyLoggable<String, LogWatch> prettyLoggable = containerResource.tailingLines(10);
//
//        LogWatch logWatch = containerResource.watchLog();
//        return logWatch;
//    }

//    public static Reader traceLog2(String namespace, String podName, String containerName) {
//        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        ContainerResource<String, LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream> containerResource = kc.pods()
//                .inNamespace(namespace)
//                .withName(podName)
//                .inContainer(containerName);
//        Reader logReader = containerResource.getLogReader();
//        return logReader;
//    }

    public static String getLog(String namespace, String podName, String containerName) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        ContainerResource<String, LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream> containerResource = kc.pods()
//                .inNamespace(namespace)
//                .withName(podName)
//                .inContainer(containerName);
        ContainerResource<LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream, Boolean> containerResource = kc.pods().inNamespace(namespace)
                .withName(podName)
                .inContainer(containerName);
        String log = containerResource.getLog();
        return log;
    }

    public static String getLog(String namespace, String podName, String containerName, int beforeMinutes, int maxSize) {
        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        ContainerResource<String, LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream> containerResource = kc.pods()
//                .inNamespace(namespace)
//                .withName(podName)
//                .inContainer(containerName);
        ContainerResource<LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream, Boolean> containerResource = kc.pods().inNamespace(namespace).withName(podName).inContainer(containerName);
        String log = containerResource.limitBytes(maxSize * 1024 * 1024).sinceSeconds(beforeMinutes * 60).getLog();
        return log;
    }

//    public static void test() throws FileNotFoundException {
//        @Cleanup KubernetesClient kc = K8sClient.getInstance();
//        String path = "D:/tmp/tmp.txt";
//        String path2 = "D:/tmp/tmp2.txt";
//        InputStream is = new FileInputStream(path2);
//        OutputStream os = new FileOutputStream(path);
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ByteArrayOutputStream error = new ByteArrayOutputStream();
//        Execable<String, ExecWatch> aDefault = kc.pods()
//                .inNamespace("default")
//                .withName("test2-test2-795565f459-7r6vk")
//                .inContainer("test2-test-instance").writingOutput(out).writingError(error).usingListener(new MyPodExecListener());
//
//        ContainerResource<LogWatch, InputStream, PipedOutputStream, OutputStream, PipedInputStream, String, ExecWatch, Boolean, InputStream, Boolean> aDefault1 = kc.pods()
//                .inNamespace("default")
//                .withName("test2-test2-795565f459-7r6vk")
//                .inContainer("test2-test-instance");
//
//        ExecWatch sh = aDefault.exec("pwd");
//
//        OutputStream input = sh.getInput();
//        InputStream output = sh.getOutput();
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
////        try {
////            os.write("ls1\r".getBytes());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        try {
////            TimeUnit.MILLISECONDS.sleep(5000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//    }

    private static class MyPodExecListener implements ExecListener {
        @Override
        public void onOpen(Response response) {
            System.out.println("Shell was opened");
        }

        @Override
        public void onFailure(Throwable throwable, Response response) {
            System.out.println("Some error encountered");
//            execLatch.countDown();
        }

        @Override
        public void onClose(int i, String s) {
            System.out.println("Shell Closing");
//            execLatch.countDown();
        }
    }

    public static void test3() {
        String url = "wss://192.168.100.95:6443/api/v1/namespaces/default/pods/product-test-9556d9667-clz5n/exec?stdin=true&stdout=true&stderr=true&tty=true&command=%2Fbin%2Fsh&container=product-test-instance";
//        String url = "wss://192.168.100.95:6443/api/v1/namespaces/default/pods/product-test-9556d9667-clz5n/exec?stdin=true&stdout=true&stderr=true&tty=true";
        KubernetesClient kc = K8sClient.getInstance();
        OkHttpClient httpClient = ((DefaultKubernetesClient) kc).getHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        //开始连接
        WebSocket websocket = httpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                //连接成功...
                System.out.println(1111);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //收到消息...（一般是这里处理json）
                System.out.println(2222);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                //收到消息...（一般很少这种消息）
                System.out.println(3333);
                System.out.println(new String(bytes.toByteArray()));
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                //连接关闭...
                System.out.println(44444);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
                super.onFailure(webSocket, throwable, response);
                //连接失败...
                throwable.printStackTrace();
                System.out.println(55555);
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(3800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean success = websocket.send("pwd");
        boolean success2 = websocket.send("ls");
        System.out.println(success);
        httpClient.dispatcher().executorService().shutdown();
    }

    public static void test4() {
        String url = "wss://192.168.100.95:6443/api/v1/namespaces/default/pods/product-test-9556d9667-clz5n/exec?stdin=true&stdout=true&stderr=true&tty=true&command=%2Fbin%2Fsh&container=product-test-instance";
        KubernetesClient kc = K8sClient.getInstance();
        OkHttpClient httpClient = ((DefaultKubernetesClient) kc).getHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        requestBuilder.post(builder.build());
        Request request = requestBuilder.build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(111);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(2222);
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        //初始化
        K8sConfig k8sConfig = new K8sConfig();
        k8sConfig.setApiServerPath("https://192.168.100.95:6443")
                .setCaPath("C:/ideaProjects/cpaas-manager/ca.crt")
                .setToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJvYXJkLXVzZXItdG9rZW4tZHZrbXgiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoia3Vib2FyZC11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiNDI5MDBhMTctNjFlYi00OWM3LWI5NmItMGZjMzBkNzg2Mzk3Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmt1Ym9hcmQtdXNlciJ9.XrRIE6ZHBLE1DOJiV9S_e-uWmxaDY0cExGk1YzxVGFGK6Wh6IJFwF2fM217Is0E3TThTQE9WW2mr71bxa5-ZbCshO5VbDFNtv4pq4Ve_7ijYdd2zPVPabH59vdDmnMt5xIVeeFwcSXN8TrrKzTcR3wnpqafGAnEYTeHcu0Z1GyTYN_y5b0PqhIdEgwMKpL-_PWClY7nta7nzwS0CDulboimpmwsIZldLkNWcLzkM90FJttvivYDDrpLkdURoHLWxnxlf_hlFnfA7LXt7v380sS--Yg8ULl6rE7Gwtk_6I77q8eKMuo55okiBq-9fKVmcDUD3SD27zgAL5-_bFN2z6w");
        K8sClient.setConfig(k8sConfig);
        test3();
    }

}
