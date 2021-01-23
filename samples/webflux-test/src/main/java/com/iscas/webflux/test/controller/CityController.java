package com.iscas.webflux.test.controller;

import com.iscas.webflux.test.domain.City;
import com.iscas.webflux.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/23 12:14
 * @since jdk1.8
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping
    public Mono<City> save(@RequestBody City city) {
        return cityService.add(city);
    }

    @DeleteMapping
    public Mono<Void> delete(Integer id) {
        City city = new City();
        city.setId(id);
        return cityService.delete(city);
    }

    @PutMapping
    public Mono<City> edit(@RequestBody City city) {
        return cityService.edit(city);
    }

    @GetMapping("/{name}")
    public Flux<City> search(@PathVariable String name) {
        return cityService.search(name);
    }
}
