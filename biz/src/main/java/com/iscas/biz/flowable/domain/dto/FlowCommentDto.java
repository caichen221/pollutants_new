package com.iscas.biz.flowable.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 9:19
 * @since jdk11
 */
@Data
@Builder
public class FlowCommentDto implements Serializable {

    /**
     * 意见类别 0 正常意见  1 退回意见 2 驳回意见
     */
    private String type;

    /**
     * 意见内容
     */
    private String comment;
}
