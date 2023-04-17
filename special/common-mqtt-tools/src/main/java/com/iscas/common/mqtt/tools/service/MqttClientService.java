package com.iscas.common.mqtt.tools.service;

import cn.hutool.core.util.StrUtil;
import com.iscas.common.mqtt.tools.*;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 9:14
 */
public class MqttClientService {
    /**
     * mqtt连接配置
     */
    private final MqttConfig mqttConfig;

    private MqttConnectionPool<MqttConnection> mqttPool;

    public MqttClientService(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    public void init() {
        try {
            // 连接池配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            this.initPoolConfig(poolConfig);

            // mqtt连接配置
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(this.mqttConfig.getUserName());
            if (StrUtil.isNotEmpty(mqttConfig.getPassword())) {
                connOpts.setPassword(this.mqttConfig.getPassword().toCharArray());
            }

            // 创建工厂对象
            MqttConnectionFactory connectionFactory = new MqttConnectionFactory(mqttConfig.getHost(), connOpts);

            // 创建连接池
            mqttPool = new MqttConnectionPool<>(connectionFactory, poolConfig);

        } catch (Exception e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void initPoolConfig(GenericObjectPoolConfig poolConfig) {
        MqttPoolConfig mqttConnectionPoolConfig = this.mqttConfig.getPoolConfig();
        if (mqttConnectionPoolConfig.isCustomSet()) {
            // 设置连接池配置信息
            poolConfig.setMinIdle(mqttConnectionPoolConfig.getMinIdle());
            poolConfig.setMaxIdle(mqttConnectionPoolConfig.getMaxIdle());
            poolConfig.setMaxTotal(mqttConnectionPoolConfig.getMaxTotal());
        }
    }

    /**
     * 根据key找到对应连接
     */
    public MqttConnection getConnection() throws Exception {
        return this.mqttPool.borrowObject();
    }

    public void publish(String clientId, String message) throws Exception {
        MqttConnection connection = null;
        try {
            connection = getConnection();
            connection.publish(clientId, message);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }

}
