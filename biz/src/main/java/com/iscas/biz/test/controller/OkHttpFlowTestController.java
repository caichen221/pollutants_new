package com.iscas.biz.test.controller;

import com.iscas.base.biz.config.okhttp.OkHttpProps;
import com.iscas.base.biz.service.common.OkHttpCustomClient;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import okhttp3.*;
import org.apache.velocity.runtime.directive.contrib.For;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * okhttp文件流式传输测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/23 15:51
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/okhttp/flow")
public class OkHttpFlowTestController {

    @GetMapping
    public void download() throws Exception {
        FileDownloadUtils.downFile(SpringUtils.getRequest(), SpringUtils.getResponse(),
                "D:\\文档资料\\_部署安装\\离线安装k8s\\1、安装k8s集群\\1.20.2安装.zip", "test.zip");
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        for (int i = 0; i < 20; i++) {
//            TimeUnit.SECONDS.sleep(1);
//        }
//        OkHttpCustomClient okHttpCustomClient = new OkHttpCustomClient(new OkHttpProps());
//        String url = "http://192.168.100.88:7901/demo/test/okhttp/flow";
//        Request.Builder requestBuilder = new Request.Builder();
//        requestBuilder.url(url);
//        Request request = requestBuilder.build();
//        Call call = okHttpCustomClient.getClient().newCall(request);
//        FileOutputStream os = new FileOutputStream("D:/tttt.zip");
//        byte[] buf = new byte[2048];
//        int len = 0;
//        try (
//                InputStream is = call.execute().body().byteStream();
//        ) {
//            while ((len = is.read(buf)) != -1) {
//                os.write(buf, 0, len);
//                os.flush();
//            }
//            os.close();
//        }
////        byte[] bytes = call.execute().body().bytes();
//
//        while (true) {
//
//        }
//    }

}
