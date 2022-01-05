package com.iscas.common.jredisearch.tools;

import com.iscas.common.redis.tools.ConfigInfo;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.RedisInfo;
import com.iscas.common.redis.tools.impl.standalone.JedisStandAloneConnection;
import io.redisearch.*;
import io.redisearch.aggregation.AggregationBuilder;
import io.redisearch.aggregation.SortedField;
import io.redisearch.aggregation.reducers.Reducers;
import io.redisearch.client.Client;
import io.redisearch.client.IndexDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/4 13:24
 * @since jdk1.8
 */
class JredisearchUtilsTest {

    private Client client = null;

    @BeforeEach
    public void before() {
        JedisConnection jedisConnection = new JedisStandAloneConnection();
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setMaxIdle(10);
        configInfo.setMaxTotal(50);
        configInfo.setMaxWait(20000);
        RedisInfo redisInfo = new RedisInfo();
        redisInfo.setHost("172.16.10.160");
        redisInfo.setPort(31158);
//        redisInfo.setPwd("iscas");
        redisInfo.setTimeout(10000);
        configInfo.setRedisInfos(Arrays.asList(redisInfo));
        client = JredisearchUtils.initJredisearchClient(jedisConnection, configInfo, "test-index2");
    }

    /**
     * 测试获取连接
     */
    @Test
    public void testConnection() {
        System.out.println(client);
    }

    /**
     * 测试创建索引
     */
    @Test
    public void createIndex() {
        Schema schema = new Schema()
                .addTextField("title", 5.0)
                .addTextField("body", 1.0)
                .addNumericField("score");
//        IndexDefinition def = new IndexDefinition()
//                .setPrefixes(new String[]{"item:", "goods:"})
//                .setFilter("@score>10");
        boolean flag = client.createIndex(schema, Client.IndexOptions.defaultOptions()/*.setDefinition(def)*/);
        Assertions.assertTrue(flag);
    }

    /**
     * 测试添加文档
     */
    @Test
    public void addDoc() {
//        Map<String, Object> fields = new HashMap<>();
//        fields.put("title", "锄禾");
//        fields.put("type", "诗歌");
//        fields.put("body", "锄禾日当午，汗滴禾下土。谁知盘中餐，粒粒皆辛苦。");
//        fields.put("score", 20);

        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "aaa");
        fields.put("tp", "test");
        fields.put("body", "aaaaaaa");
        fields.put("score", 20);
        boolean b = client.addDocument("6", fields);
        Assertions.assertTrue(b);
    }

    /**
     * 创建复杂查询
     */
    @Test
    public void search() {
        Query query = new Query("锄禾")
                .addFilter(new Query.NumericFilter("score", 0, 10000))
                .limit(0, 100);
        SearchResult searchResult = client.search(query);
        List<Document> docs = searchResult.docs;
        docs.forEach(System.out::println);
    }

    /**聚合查询*/
    @Test
    public void aggregate() {
        AggregationBuilder builder = new AggregationBuilder("aaa")
                .apply("@".concat("score").concat("/10"), "k")
                .groupBy("@".concat("body"), Reducers.avg("@".concat("k")).as("avgprice"))
                .filter("@".concat("avgprice").concat(">=1"))
                .sortBy(10, SortedField.asc("@".concat("tp")));
        AggregationResult aggregate = client.aggregate(builder);
        System.out.println(aggregate);
    }

    /**
     * 分词查询 先分词 在合并
     */
//    public List<SearchResult> searchParticiple(String queryString, String price){
//        List<SearchResult> result  = new ArrayList<>();
//        //分词
//        List<Term> termList = NLPTokenizer.segment("aaa");
//        termList.stream().forEach(e->{
//            Query query = new Query(e.word)
//                    .addFilter(new Query.NumericFilter(price, 0, 1000))
//                    .limit(0,Integer.MAX_VALUE);
//            SearchResult search = client.search(query);
//            result.add(search);
//        });
//        return result;
//    }


}