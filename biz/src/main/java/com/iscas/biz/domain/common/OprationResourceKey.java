package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "opration_resource")
@Data
public class OprationResourceKey {
    private Integer opId;

    private Integer resourceId;
}