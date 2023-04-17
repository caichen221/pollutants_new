package com.iscas.common.mqtt.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.HostInfo;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 8:34
 */
public class MqttConnectionFactory extends BasePooledObjectFactory<MqttConnection> {

    private AtomicInteger counter = new AtomicInteger();

    /**
     * 连接地址
     */
    private String serverURI;
    /**
     * 当前服务IP
     */
    private String localHostIP;

    /**
     * mqtt连接配置
     */
    private MqttConnectOptions mqttConnectConfig;

    /**
     * 根据mqtt连接 配置创建工厂
     */
    public MqttConnectionFactory(String serverURI, MqttConnectOptions mqttConnectConfig) {
        this.serverURI = serverURI;
        this.mqttConnectConfig = mqttConnectConfig;
    }

    /**
     * 创建对象
     */
    @Override
    public MqttConnection create() throws MqttException {
        // 实现线程安全避免在高并发的场景下出现clientId重复导致无法创建连接的情况
        int count = this.counter.addAndGet(1);

        // 根据ip+编号,生成唯一clientId
        String clientId = this.getLosthostIp() + "_" + System.currentTimeMillis();

        // 创建MQTT连接对象
        MqttClient mqttClient = new MqttClient(serverURI, clientId);

        // 建立连接
        mqttClient.connect(mqttConnectConfig);

        // 构建mqttConnection对象
        MqttConnection mqttConnection = new MqttConnection(mqttClient);
//        System.out.println("在对象池中创建对象 " + clientId);
        return mqttConnection;
    }

    /**
     * common-pool2 中创建了 DefaultPooledObject 对象对对象池中对象进行的包装。
     * 将我们自定义的对象放置到这个包装中，工具会统计对象的状态、创建时间、更新时间、返回时间、出借时间、使用时间等等信息进行统计
     */
    @Override
    public PooledObject<MqttConnection> wrap(MqttConnection mqttConnection) {
//        System.out.println("封装默认返回类型 " + mqttConnection.toString());
        return new DefaultPooledObject<>(mqttConnection);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<MqttConnection> p, DestroyMode destroyMode) throws Exception {
        if (p == null) {
            return;
        }
        MqttConnection mqttConnection = p.getObject();
//        System.out.println("销毁对象 " + p.getObject().getMqttClient());
        mqttConnection.destroy();
    }

    /**
     * 校验对象是否可用
     */
    @Override
    public boolean validateObject(PooledObject<MqttConnection> p) {
        MqttConnection mqttConnection = p.getObject();
        boolean result = mqttConnection.getMqttClient().isConnected();
//        System.out.printf("validateObject serverURI %s,client_id %s,result %s%n", mqttConnection.getMqttClient().getServerURI(),
//                mqttConnection.getMqttClient().getClientId(), result);
        return result;
    }

    /**
     * 激活钝化的对象系列操作
     */
    @Override
    public void activateObject(PooledObject<MqttConnection> p) {
//        System.out.println("激活钝化的对象 " + p.getObject().getMqttClient());
    }

    /**
     * 钝化未使用的对象
     */
    @Override
    public void passivateObject(PooledObject<MqttConnection> p) {
//        System.out.println("钝化未使用的对象 " + p.getObject().getMqttClient());
    }

    /**
     * 获取当前服务真实IP
     */
    private String getLosthostIp() {
        if (StrUtil.isNotBlank(this.localHostIP)) {
            return this.localHostIP;
        }
        HostInfo hostInfo = new HostInfo();
        this.localHostIP = hostInfo.getAddress();
        return this.localHostIP;
    }
}
