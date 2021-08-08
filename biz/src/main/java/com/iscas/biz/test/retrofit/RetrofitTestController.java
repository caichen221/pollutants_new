package com.iscas.biz.test.retrofit;

import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/8 20:58
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/retrofit")
public class RetrofitTestController {
    @Autowired
    private RetrofitApi retrofitApi;
    @GetMapping("t1")
    public ResponseEntity t1() {
        return retrofitApi.t1("quanwen");
    }
}
