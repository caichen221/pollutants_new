package com.iscas.biz.test.controller;

import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/23 20:52
 * @since jdk1.8
 */
@RestController
@RequestMapping("/down/file")
public class DownloadController {
    @GetMapping("/tt")
    public void down() throws Exception {
        FileDownloadUtils.downByStream(SpringUtils.getRequest(), SpringUtils.getResponse(), new FileInputStream("D:\\soft\\test-quick-frame\\newframe-tttt\\my-checkstyle.xml"), "check.xml");
    }
}
