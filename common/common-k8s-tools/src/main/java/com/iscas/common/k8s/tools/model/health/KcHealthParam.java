package com.iscas.common.k8s.tools.model.health;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 15:06
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcHealthParam {
    /**
     * 延迟探测时间（秒）
     * */
    protected int initialDelaySeconds;

    /**
     * 执行测试频率(默认为10， 最小为1)
     * */
    protected int periodSeconds = 10;

    /**
     * 超时时间(秒)
     * */
    protected int timeout;

    /**
     * 健康阈值(默认为1，最小为1)
     * */
    protected int healthThreshold = 1;

    /**
     * 不健康阈值(默认为3，最小为1)
     * */
    protected int unHealthThreshold = 3;
}
