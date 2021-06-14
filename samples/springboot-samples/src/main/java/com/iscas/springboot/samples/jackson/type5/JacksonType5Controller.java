package com.iscas.springboot.samples.jackson.type5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 14:08
 * @since jdk1.8
 */
@RestController
@RequestMapping("/jackson/type5")
public class JacksonType5Controller {
    @GetMapping("/res")
    public Model res() {
        return Model.builder()
                .id(1)
                .age(12)
                .name("xiaoxiao")
                .createTime(new Date()).build();
    }
}
