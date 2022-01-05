package com.iscas.common.jredisearch.tools;


import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import io.redisearch.Schema;
import io.redisearch.client.Client;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 全文检索工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/4 10:18
 * @since jdk1.8
 */
public class JredisearchUtils {
    private JredisearchUtils() {}

    private static volatile Client client;

    public static Client initJredisearchClient(JedisConnection jedisConnection, ConfigInfo configInfo, String indexName) {

        if (client == null) {
            synchronized (JredisearchUtils.class) {
                if (client == null) {
                    if (!(jedisConnection instanceof JedisStandAloneConnection)) {
                        throw new UnsupportedOperationException("当前只支持JedisStandAloneConnection类型");
                    }
                    jedisConnection.initConfig(configInfo);
                    JedisPool pool = (JedisPool) jedisConnection.getPool();
                    client = new Client(indexName, pool);
                }
            }
        }
        return client;
    }

//    /**
//     * 创建索引
//     */
//    public void createIndex(String title, String body, String numericField, Client.IndexOptions indexOptions){
//        Schema sc = new Schema()
//                .addTextField(title, 5.0)
//                .addTextField(body, 1.0)
//                .addNumericField(numericField);
//        client.createIndex(sc, indexOptions);
//    }


}
