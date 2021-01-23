package com.iscas.webflux.test.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/23 12:03
 * @since jdk1.8
 */
@Table("city")
@Data
public class City {
    @Id
    private Integer id;

    private String name;
    private String countrycode;
    private String district;
    private Integer population;
}
