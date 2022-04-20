package com.iscas.biz.flowable.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:25
 * @since jdk11
 */
@Data
public class FlowViewerDto implements Serializable {
    private String key;
    private boolean completed;
}
