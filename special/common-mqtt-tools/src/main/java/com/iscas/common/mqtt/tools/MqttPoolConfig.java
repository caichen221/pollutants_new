package com.iscas.common.mqtt.tools;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 8:10
 */
@Data
@Accessors(chain = true)
public class MqttPoolConfig {

    /**
     * 是否启用自定义配置
     */
    private boolean customSet;
    /**
     * 最小的空闲连接数
     */
    private int minIdle;
    /**
     * 最大的空闲连接数
     */
    private int maxIdle;
    /**
     * 最大连接数
     */
    private int maxTotal;
}
