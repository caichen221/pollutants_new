package com.iscas.biz.test.controller;

import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import io.swagger.annotations.ApiParam;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/9/14 10:59
 * @since jdk11
 */
@RestController
@RequestMapping("/pathvariable/test")
public class PathVariableTest {
    @GetMapping("/{test}")
    public String test(@PathVariable String test) {
        System.out.println(test);
        return test;
    }

    @PostMapping("/test2/{test}")
    public String test2(@PathVariable String test, @RequestBody String str) {
        System.out.println(test);
        System.out.println(str);
        return test;
    }

    @PostMapping("/test3/{test}")
    public String test2(@PathVariable String test, MultipartFile file) {
        System.out.println(test);
        System.out.println(file);
        return test;
    }

    @PostMapping("/{datasourceType}/{datasourceName}/upload")
    public ResponseEntity upload(@ApiParam(value = "数据源类型") @PathVariable String datasourceType,
                                 @ApiParam(value = "数据源名称") @PathVariable String datasourceName,
                                 @ApiParam(value = "文件父路径") @RequestParam String parentPath,
                                 @ApiParam(value = "文件") MultipartFile[] files) throws BaseException {
        System.out.println(datasourceName);
        return new ResponseEntity();
    }
}
