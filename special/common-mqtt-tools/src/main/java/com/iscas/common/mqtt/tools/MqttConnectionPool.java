package com.iscas.common.mqtt.tools;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/17 9:12
 */
public class MqttConnectionPool<T> extends GenericObjectPool<MqttConnection> {
    public MqttConnectionPool(MqttConnectionFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    /**
     * 从对象池获取一个对象
     */
    @Override
    public MqttConnection borrowObject() throws Exception {
        MqttConnection conn = super.borrowObject();
        // 设置所属连接池
        if (conn.getBelongedPool() == null) {
            conn.setBelongedPool(this);
        }
        return conn;
    }

    /**
     * 归还连接对象
     */
    @Override
    public void returnObject(MqttConnection conn) {
        if (conn != null) {
            super.returnObject(conn);
        }
    }
}
