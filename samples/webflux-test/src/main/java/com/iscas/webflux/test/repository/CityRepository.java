package com.iscas.webflux.test.repository;

import com.iscas.webflux.test.domain.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/23 12:07
 * @since jdk1.8
 */
public interface CityRepository extends ReactiveCrudRepository<City, Integer> {
    Flux<City> findByNameLike(String name);
}
