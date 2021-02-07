package com.iscas.rsocket.client.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

/**
 * 客户端配置类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 17:30
 * @since jdk1.8
 */
@Configuration
public class ClientConfiguration {

    @Bean
    RSocketRequester rSocketRequester(/*RSocketStrategies rSocketStrategies*/) {
        RSocketStrategies strategies = RSocketStrategies.builder()
//                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
//                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        RSocketRequester requester = RSocketRequester.builder()
                .rsocketStrategies(strategies)
                .tcp("localhost", 9789);

        return requester;
    }
}
