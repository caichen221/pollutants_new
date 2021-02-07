package com.iscas.rsocket.server.samples.reqres;

import com.iscas.rsocket.server.samples.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Request/Response模型
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 17:50
 * @since jdk1.8
 */

@Controller
public class ReqResServerController {
    @MessageMapping("findById")
    public Mono<User> currentMarketData(User user) {
        Integer id = user.getId();
        for (User user1 : User.users) {
            if (Objects.equals(user1.getId(), id)) {
                return Mono.just(user1);
            }
        }
        return Mono.empty();
    }
}
