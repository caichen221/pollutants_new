package com.iscas.common.mqtt.tools.service;

import com.iscas.common.mqtt.tools.MqttConfig;
import com.iscas.common.mqtt.tools.MqttPoolConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 9:40
 */
class MqttClientServiceTest {
    private MqttClientService mqttClientService;

    @BeforeEach
    public void before() {
        // 初始化连接
        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.setHost("tcp://192.168.100.22:61613")
                .setClientId("test-client")
                .setUserName("admin")
                .setPassword("password");
        MqttPoolConfig mqttPoolConfig = new MqttPoolConfig();
        mqttPoolConfig.setCustomSet(true)
                .setMinIdle(2)
                .setMaxIdle(5)
                .setMaxTotal(10);
        mqttConfig.setPoolConfig(mqttPoolConfig);
        mqttClientService = new MqttClientService(mqttConfig);
        mqttClientService.init();
    }


    @Test
    public void test() throws Exception {
        MqttClient mqttClient = mqttClientService.getConnection().getMqttClient();
        //这里的setCallback需要新建一个Callback类并实现 MqttCallback 这个类
//        mqttClient.setCallback(new PushCallback());

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                String topic = "test-topic";
                try {
                    mqttClientService.publish(topic, "发送消息：" + System.currentTimeMillis());
                    Thread.sleep(3000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        while (true) {
            mqttClient.setCallback(new PushCallback());
            mqttClient.subscribe("test-topic");
            Thread.sleep(1000);
        }

    }

}