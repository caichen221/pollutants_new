package com.iscas.biz.domain.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Resource {
    private Integer resourceId;

    private String resourceUrl;

    private Date resourceCreateTime;

    private Date resourceUpdateTime;

    private String resourceDesc;

}