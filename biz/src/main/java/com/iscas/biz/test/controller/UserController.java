package com.iscas.biz.controller;


import com.iscas.biz.model.User;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @GetMapping("/t1")
    public ResponseEntity t1() {
        return new ResponseEntity();
    }

    @GetMapping("/t2")
    public ResponseEntity t2(String str) {
        ResponseEntity response = getResponse();
        response.setValue(str);
        return response;
    }

    @PostMapping("/t3")
    public ResponseEntity t3(@RequestBody User user) {
        ResponseEntity response = getResponse();
        response.setValue(user);
        return response;
    }
}

