package com.iscas.rsocket.client.samples.fireforget;

import com.iscas.rsocket.client.samples.model.User;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 19:03
 * @since jdk1.8
 */
@RestController
public class FireForgetClientController {
    private final RSocketRequester rSocketRequester;

    public FireForgetClientController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping(value = "/add")
    public Publisher<Void> collect() {
        return rSocketRequester
                .route("add")
                .data(new User(11, "test-user1", 23))
                .send();
    }
}
