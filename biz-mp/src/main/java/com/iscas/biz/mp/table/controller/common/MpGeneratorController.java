package com.iscas.biz.mp.table.controller.common;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.service.common.MpGenerator;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mybatis-generator controller
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/10/11 16:59
 * @since jdk1.8
 */
@RestController
@RequestMapping("/mp")
@ConditionalOnMybatis
public class MpGeneratorController extends BaseController {
    @Autowired
    private MpGenerator mpGenerator;
    @GetMapping("/generator")
    public ResponseEntity generator(){
        mpGenerator.generator();
        return getResponse();
    }
}
