package com.iscas.biz.test.feign.remote;

import com.iscas.base.biz.util.SpringUtils;
import com.iscas.biz.test.retrofit.RemoteRetrofitController;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 *
 *  测试远程被调用的feign接口
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/13 9:26
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/feign/remote")
public class RemoteFeignController extends BaseController {

    @GetMapping("/t1")
    public ResponseEntity t1(@RequestParam("name") String name) {
        ResponseEntity response = getResponse();
        response.setValue(name);
        return response;
    }

    @DeleteMapping("/t2/{name}")
    public ResponseEntity t2(@PathVariable("name") String name) {
        ResponseEntity response = getResponse();
        response.setValue(name);
        return response;
    }

    @PostMapping("/t3")
    public ResponseEntity t3(@RequestBody Map<String, Object> params) {
        ResponseEntity response = getResponse();
        response.setValue(params);
        return response;
    }

    @PutMapping("/t4")
    public ResponseEntity t4(@RequestBody Map<String, Object> params) {
        ResponseEntity response = getResponse();
        response.setValue(params);
        return response;
    }

    @PostMapping("/t5")
    public ResponseEntity t5(@RequestParam Map<String, Object> params) {
        ResponseEntity response = getResponse();
        response.setValue(params);
        return response;
    }

    @PostMapping("/t6")
    public ResponseEntity t6(RemoteRetrofitController.RetrofitTestModel model) {
        ResponseEntity response = getResponse();
        response.setValue(model);
        return response;
    }

    @PostMapping("/t7")
    public ResponseEntity t7(String name) {
        ResponseEntity response = getResponse();
        response.setValue(name);
        return response;
    }

    @PostMapping("/t8")
    public ResponseEntity t8(@RequestHeader("name") String name) {
        ResponseEntity response = getResponse();
        response.setValue(name);
        return response;
    }

    @PostMapping("/t9")
    public ResponseEntity t9(@RequestHeader("name") String name, @RequestHeader("token") String token) {
        ResponseEntity response = getResponse();
        response.setValue(name + ";" + token);
        return response;
    }

    @PostMapping("/t10")
    public ResponseEntity t10(MultipartFile file1) {
        ResponseEntity response = getResponse();
        response.setValue(file1.toString());
        return response;
    }

    @PostMapping("/t11")
    public ResponseEntity t11() throws ServletException, IOException {
        HttpServletRequest request = SpringUtils.getRequest();
        Collection<Part> parts = request.getParts();
        if (parts != null) {
            for (Part part : parts) {
                InputStream inputStream = part.getInputStream();
            }
        }
        ResponseEntity response = getResponse();
        response.setValue(parts.toString());
        return response;
    }

    @PostMapping("/t12")
    public void t12() throws Exception {
        FileDownloadUtils.downFile(SpringUtils.getRequest(), SpringUtils.getResponse(),
                "D:/test-sp/aaa.html", "aaa.html");
    }

    @Data
    public static class RetrofitTestModel {
        private String name;
    }

}
