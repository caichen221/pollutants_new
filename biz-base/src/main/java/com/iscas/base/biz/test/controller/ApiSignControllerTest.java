package com.iscas.base.biz.test.controller;

import com.iscas.base.biz.util.ApiSignUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseRuntimeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/2/21 14:29
 * @since jdk1.8
 */
@RestController
@RequestMapping("/api/sign")
public class ApiSignControllerTest extends BaseController {

    @GetMapping("/t1")
    public ResponseEntity t1(HttpServletRequest request, String username, String password) throws IOException {
        return getResponse().setValue("username=" + username + "&password=" + password);
    }

    @PostMapping("/t2")
    public ResponseEntity t2(HttpServletRequest request, @RequestBody Map<String, Object> body) throws IOException {
        return getResponse().setValue(body);
    }
}
