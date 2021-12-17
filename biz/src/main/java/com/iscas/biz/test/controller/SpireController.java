package com.iscas.biz.test.controller;

import com.iscas.common.tools.office.iceblue.ReadWord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/17 17:30
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/spire")
public class SpireController {

    @GetMapping
    public String test() {
        return ReadWord.readWord();
    }
}
