package com.iscas.biz.controller;

import com.iscas.biz.service.MonitorService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/2 13:19
 * @since jdk1.8
 */
@RestController
@RequestMapping("/monitor")
@Api(tags = "监控管理")
public class MonitorController extends BaseController {

    @Autowired
    private MonitorService monitorService;

    @ApiOperation(value = "获取监控数据", notes = "获取物理资源、JVM等数据")
    @GetMapping(value = "/getData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getData() throws BaseException {
        ResponseEntity response = getResponse();
        try {
            Object result = monitorService.getData();
            response.setValue(result);

        } catch (Exception e) {
            throw new BaseException("获取监控数据出错", e);
        }
        return response;
    }
}
