package com.iscas.common.mqtt.tools;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 8:09
 */
@Data
@Accessors(chain = true)
public class MqttConfig {
    /**
     * MQTT host 地址 如：tcp://192.168.100.22:61613
     */
    private String host;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 登录用户(可选)
     */
    private String userName;

    /**
     * 登录密码(可选)
     */
    private String password;

    /**
     * Mqtt Pool Config
     */
    private MqttPoolConfig poolConfig;

}
