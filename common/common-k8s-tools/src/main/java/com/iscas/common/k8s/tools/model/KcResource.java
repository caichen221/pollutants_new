package com.iscas.common.k8s.tools.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 15:20
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcResource {
    /**
     * cpu最小 M
     * */
    private int cpuMin;

    /**
     * cpu最大
     * */
    private int cpuMax;

    /**
     * 内存最小 M
     * */
    private int memoryMin;

    /**
     * 内存最大 M
     * */
    private int memoryMax;

}
