package com.iscas.rsocket.server.samples.fireforget;

import com.iscas.rsocket.server.samples.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * FIRE/FORGET模式
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 18:59
 * @since jdk1.8
 */
@Controller
public class FireForgetServerController {

    @MessageMapping("add")
    public Mono<Void> collectMarketData(User user) {
        User.users.add(user);
        return Mono.empty();
    }
}
