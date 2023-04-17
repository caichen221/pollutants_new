package com.iscas.common.mqtt.tools;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 8:28
 */
public class MqttConnection {
    private MqttClient mqttClient;

    public MqttConnection(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    /**
     * 隶属于的连接池
     */
    private GenericObjectPool<MqttConnection> belongedPool;

    /**
     * 推送方法消息
     */
    public void publish(String topic, String message) throws Exception {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttClient.publish(topic, mqttMessage);
//        System.out.println("对象：" + mqttClient + " " + "发送消息：" + message);
    }

    /**
     * 销毁连接
     */
    public void destroy() {
        try {
            if (this.mqttClient.isConnected()) {
                this.mqttClient.disconnect();
            }
            this.mqttClient.close();
        } catch (Exception e) {
//            System.err.println("MqttConnection destroy ERROR ; errorMsg=" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 将连接交回给连接池
     */
    public void close() {
        if (belongedPool != null) {
            this.belongedPool.returnObject(this);
        }
    }


    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public GenericObjectPool<MqttConnection> getBelongedPool() {
        return belongedPool;
    }

    public void setBelongedPool(GenericObjectPool<MqttConnection> belongedPool) {
        this.belongedPool = belongedPool;
    }
}
