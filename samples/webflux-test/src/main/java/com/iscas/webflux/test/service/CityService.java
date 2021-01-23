package com.iscas.webflux.test.service;

import com.iscas.webflux.test.domain.City;
import com.iscas.webflux.test.repository.CityRepository;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/23 12:09
 * @since jdk1.8
 */
@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public Mono<City> add(City city) {
        return cityRepository.save(city);
    }

    public Mono<City> edit(City city) {
        return cityRepository.save(city);
    }

    public Mono<Void> delete(City city) {
        return cityRepository.delete(city);
    }

    public Flux<City> search(String name) {
        return cityRepository.findByNameLike(name);
    }

}
