package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.K8sClient;
import com.iscas.common.k8s.tools.cfg.K8sConfig;
import io.fabric8.kubernetes.client.dsl.LogWatch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/14 15:56
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class KcExecUtilsTests {

    @Before
    public void before() {
        //初始化
        K8sConfig k8sConfig = new K8sConfig();
        k8sConfig.setApiServerPath("https://192.168.100.95:6443")
                .setCaPath("C:/ideaProjects/cpaas-manager/ca.crt")
                .setToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJvYXJkLXVzZXItdG9rZW4tZHZrbXgiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoia3Vib2FyZC11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiNDI5MDBhMTctNjFlYi00OWM3LWI5NmItMGZjMzBkNzg2Mzk3Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmt1Ym9hcmQtdXNlciJ9.XrRIE6ZHBLE1DOJiV9S_e-uWmxaDY0cExGk1YzxVGFGK6Wh6IJFwF2fM217Is0E3TThTQE9WW2mr71bxa5-ZbCshO5VbDFNtv4pq4Ve_7ijYdd2zPVPabH59vdDmnMt5xIVeeFwcSXN8TrrKzTcR3wnpqafGAnEYTeHcu0Z1GyTYN_y5b0PqhIdEgwMKpL-_PWClY7nta7nzwS0CDulboimpmwsIZldLkNWcLzkM90FJttvivYDDrpLkdURoHLWxnxlf_hlFnfA7LXt7v380sS--Yg8ULl6rE7Gwtk_6I77q8eKMuo55okiBq-9fKVmcDUD3SD27zgAL5-_bFN2z6w");
        K8sClient.setConfig(k8sConfig);
    }

//    @Test
//    public void test() throws FileNotFoundException {
//        KcExecUtils.test();
//    }

    @Test
    public void testTraceLog() throws IOException, InterruptedException {
        LogWatch logWatch = KcExecUtils.traceLog("default", "graalvm-test-76d5d69b67-jh5rk", "graalvm-test-instance", KcExecUtils.LOG_WATCH_LAST_LINES);
        InputStream output = logWatch.getOutput();
        byte[] buff = new byte[1024];
        new Thread(() -> {
            while (true) {
                int read = 0;
                try {
                    read = output.read(buff);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print(new String(buff, 0, read));
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(5000);
        logWatch.close();

    }

    @Test
    public void testTraceLog2() throws IOException {
        for (int i = 0; i < 10; i++) {
            LogWatch logWatch = KcExecUtils.traceLog("default", "graalvm-test-76d5d69b67-jh5rk", "graalvm-test-instance", KcExecUtils.LOG_WATCH_LAST_LINES);
            new Thread(() -> {
                byte[] buff = new byte[1024];
                while (true) {
                    int read = 0;
                    try {
                        read = logWatch.getOutput().read(buff);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.print(new String(buff, 0, read));
                }
            }).start();
            System.out.println(logWatch);
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLog() throws IOException {
        String logs = KcExecUtils.getLog("default", "product-test-9556d9667-f8tjc", "product-test-instance");

        System.out.println(logs);

    }
}
