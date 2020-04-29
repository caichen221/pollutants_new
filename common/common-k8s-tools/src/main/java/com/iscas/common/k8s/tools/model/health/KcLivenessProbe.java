package com.iscas.common.k8s.tools.model.health;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 存活检测
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 15:04
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcLivenessProbe {
    /**
     * 就绪检测类型 HTTP / TCP / COMMAND
     * */
    private String type;

    /**
     * 参数
     * */
    private KcHealthParam healthParam;
}
