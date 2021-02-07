package com.iscas.rsocket.server.samples.reqstream;

import com.iscas.rsocket.server.samples.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 *
 * Request/Stream模式
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 19:11
 * @since jdk1.8
 */
@Controller
public class ReqStreamServerController {
    @MessageMapping("getAll")
    public Flux<User> feedMarketData() {
        return Flux.fromStream(User.users.stream());
    }
}
