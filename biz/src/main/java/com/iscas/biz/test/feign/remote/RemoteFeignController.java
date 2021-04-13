package com.iscas.biz.test.feign.remote;

import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
