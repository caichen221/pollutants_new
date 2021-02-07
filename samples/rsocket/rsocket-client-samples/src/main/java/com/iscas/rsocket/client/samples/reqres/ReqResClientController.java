package com.iscas.rsocket.client.samples.reqres;

import com.iscas.rsocket.client.samples.model.User;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReqResClientController {

    private final RSocketRequester rSocketRequester;

    public ReqResClientController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping(value = "/find/{id}")
    public Publisher<User> current(@PathVariable("id")Integer id) {
        return rSocketRequester
          .route("findById")
          .data(new User(id, null, 0))
          .retrieveMono(User.class);
    }
}