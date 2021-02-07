package com.iscas.rsocket.client.samples.reqstream;

import com.iscas.rsocket.client.samples.model.User;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RestController
public class ReqStreamClientController {
    @Autowired
    private RSocketRequester rSocketRequester;
    @GetMapping(value = "/getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<User> getAll() {
        return rSocketRequester
                .route("getAll")
//                .data()
                .retrieveFlux(User.class);
    }
}
