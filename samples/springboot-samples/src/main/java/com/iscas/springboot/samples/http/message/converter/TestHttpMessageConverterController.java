package com.iscas.springboot.samples.http.message.converter;

import org.springframework.web.bind.annotation.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 20:01
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/http/message/convert")
public class TestHttpMessageConverterController {

    @PostMapping(value = "/reader/writer", produces = {"application/json+converttestmodel"}, consumes = {"application/json+converttestmodel"})
    public ConverterTestModel readWrite(@RequestBody ConverterTestModel converterTestModel) {
        return converterTestModel;
    }
}
