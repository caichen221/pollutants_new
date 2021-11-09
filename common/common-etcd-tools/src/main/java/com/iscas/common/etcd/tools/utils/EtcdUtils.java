package com.iscas.common.etcd.tools.utils;

import com.iscas.common.etcd.tools.exception.EtcdClientException;
import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.kv.TxnResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.lock.UnlockResponse;
import io.etcd.jetcd.op.Op;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.netty.handler.ssl.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ETCD操作工具类
 * <p>
 * 从k8s的master节点/etc/kubernetes/pki/etcd/ 拷贝server.crt、peer.crt到本地。
 * 在linux运行openssl pkcs8 -topk8 -nocrypt -in peer.key –out peer-pkcs8.key，
 * 将生成的peer-pkcs8.key拷贝到本地。
 */
public class EtcdUtils {

    public static String trustManagerPath = "classpath:ca.crt";
    public static String keyCertChainPath = "classpath:peer.crt";
    public static String keyPath = "classpath:peer-pkcs8.key";
    public static String etcdAddr = "https://172.16.10.160:2379";
    public static String etcdDomain = "172.16.10.160";

    private static volatile Client client;
    private static long TIME_OUT = 3000L;
    private static TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static KV kvClient;
    private static Lock lockClient;
    private static Lease leaseClient;

    private EtcdUtils() {
    }

    public static SslContext openSslContext() throws EtcdClientException {
        // 证书、客户端证书、客户端私钥
        try {
            InputStream trustManagerFile = ConfigUtils.getConfigIs(trustManagerPath);
            InputStream keyCertChainFile = ConfigUtils.getConfigIs(keyCertChainPath);
            InputStream KeyFile = ConfigUtils.getConfigIs(keyPath);
            // 这里必须要设置alpn,否则会提示ALPN must be enabled and list HTTP/2 as a supported protocol.错误; 这里主要设置了传输协议以及传输过程中的错误解决方式
            ApplicationProtocolConfig alpn = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.ALPN,
                    ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE,
                    ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT,
                    ApplicationProtocolNames.HTTP_2);
            SslContext context = SslContextBuilder
                    .forClient()
                    // 设置alpn
                    .applicationProtocolConfig(alpn)
                    // 设置使用的那种ssl实现方式
                    .sslProvider(SslProvider.OPENSSL)
                    // 设置ca证书
                    .trustManager(trustManagerFile)
                    // 设置客户端证书
                    .keyManager(keyCertChainFile, KeyFile)
                    .build();
            return context;
        } catch (FileNotFoundException e) {
            throw new EtcdClientException("未找到证书配置文件", e);
        } catch (SSLException e) {
            throw new EtcdClientException("SSL连接出错", e);
        }
    }


    public static Client getClient() throws EtcdClientException {
        if (client == null) {
            synchronized (EtcdUtils.class) {
                if (client == null) {
                    ClientBuilder builder = Client.builder();
                    // 设置服务器地址,这里是列表
                    builder.endpoints(etcdAddr.split(","));
                    // 当服务器端开启ssl认证时则该地方的设置就没有意义了.etcd会使用客户端ca证书中的CN头作为用户名进行权限认证
                    //        if (etcdProps.getAuthority()) {
                    //            ByteSequence user = ByteSequence.from("username");
                    //            ByteSequence pwd = ByteSequence.from("password");
                    //
                    //            builder.user(user);
                    //            builder.password(pwd);
                    //        }
                    // 这个authority必填.是服务器端CA设置的可授权访问的host域名之一.
                    // https访问网站的时候，最重要的一环就是验证服务器方的证书的域名是否与我想要访问的域名一致(可查看ETCD概念入门文章了解CA证书生成)
                    builder.sslContext(openSslContext())
                            .connectTimeout(Duration.ofSeconds(3))
                            .keepaliveTimeout(Duration.ofSeconds(20))
                            .authority(etcdDomain);
                    client = builder.build();
                    kvClient = client.getKVClient();
                    lockClient = client.getLockClient();
                    leaseClient = client.getLeaseClient();

                }
            }
        }
        return client;
    }


    /**
     * 毫秒
     *
     * @param timeout
     */
    public static void setTimeOut(long timeout) {
        EtcdUtils.TIME_OUT = timeout;
    }

    /**
     * ETCD设置值传递给客户端需要ByteSequence类型对象才可以
     *
     * @param val 欲转换的值 : 可以为Key或者Value
     */
    public static ByteSequence bytesOf(String val) {
        return ByteSequence.from(val.getBytes(StandardCharsets.UTF_8));
    }

    public static String toString(ByteSequence byteSequence) {
        return byteSequence.toString(StandardCharsets.UTF_8);
    }

    /**
     * 判断当前Key是否存在
     *
     * @param key
     */
    public static Boolean exists(String key) {
        if (null == key || "".equals(key)) {
            return false;
        }
        ByteSequence byteKey = bytesOf(key);
        GetResponse response = null;
        try {
            response = kvClient.get(byteKey).get(TIME_OUT, TIME_UNIT);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
        return response.getCount() > 0;
    }

    /**
     * 设置指定K-V
     *
     * @param key
     * @param value
     */
    public static Boolean put(String key, String value) {
        if (null == key || "".equals(key)) {
            throw new NullPointerException();
        }

        CompletableFuture<PutResponse> future = kvClient.put(bytesOf(key), bytesOf(value));
        try {
            PutResponse response = future.get(TIME_OUT, TIME_UNIT);
            return null != response;
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
    }

    /**
     * 设置指定KV, 并带租约ID
     *
     * @param key
     * @param value
     * @param leaseId 租约ID
     * @return java.lang.Boolean
     * @throws
     * @version 1.0
     * @date 2021/10/28
     * @since jdk1.8
     */
    public static Boolean put(String key, String value, long leaseId) {
        if (null == key || "".equals(key)) {
            throw new NullPointerException();
        }
        CompletableFuture<PutResponse> future = kvClient.put(bytesOf(key), bytesOf(value), PutOption.newBuilder().withLeaseId(leaseId).build());
        PutResponse putResponse = null;
        try {
            putResponse = future.get(TIME_OUT, TIME_UNIT);
            return !Objects.equals(null, putResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
    }

    /**
     * 设置KV 带超时时间
     *
     * @param key
     * @param value
     * @param ttl   过期时间，毫秒
     * @return void
     * @throws
     * @version 1.0
     * @date 2021/10/28
     * @since jdk1.8
     */
    public static Boolean putAndGrant(String key, String value, long ttl) {
        LeaseGrantResponse leaseGrantResponse = null;
        try {
            leaseGrantResponse = leaseClient.grant(ttl).get(TIME_OUT, TIME_UNIT);
            return put(key, value, leaseGrantResponse.getID());
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
    }

    /**
     * 释放租约
     *
     * @param leaseId 租约ID号
     * @return void
     * @throws
     * @version 1.0
     * @date 2021/10/28
     * @since jdk1.8
     */
    public static void revokeLease(long leaseId) {
        try {
            leaseClient.revoke(leaseId).get(TIME_OUT, TIME_UNIT);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
    }


    /**
     * 获取指定Key的值
     *
     * @param key
     */
    public static String getSingle(String key) {
        if (null == key || "".equals(key)) {
            throw new NullPointerException();
        }

        ByteSequence byteKey = bytesOf(key);
        GetResponse response = null;
        try {
            response = kvClient.get(byteKey).get(TIME_OUT, TIME_UNIT);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
        if (null != response && response.getCount() > 0) {
            return response.getKvs().get(0).getValue().toString(StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    /**
     * 获取指定Key前缀的KV映射表
     *
     * @param prefix
     */
    public static Map<String, String> getWithPrefix(String prefix) {
        if (null == prefix || "".equals(prefix)) {
            throw new NullPointerException();
        }

        ByteSequence prefixByte = bytesOf(prefix);
        GetOption getOption = GetOption.newBuilder().withPrefix(prefixByte).build();
        GetResponse response = null;
        try {
            response = kvClient.get(prefixByte, getOption).get(TIME_OUT, TIME_UNIT);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }

        Map<String, String> kvMap = new HashMap<>();
        if (null != response && response.getCount() > 0) {
            response.getKvs().forEach(item -> kvMap.put(toString(item.getKey()), toString(item.getValue())));
        }
        return kvMap;
    }

    /**
     * 删除指定Key
     */
    public static Boolean delSingle(String key) {
        if (null == key || "".equals(key)) {
            throw new NullPointerException();
        }
        long deleted = 0;
        try {
            deleted = kvClient.delete(bytesOf(key)).get(TIME_OUT, TIME_UNIT).getDeleted();
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
        return deleted > 0;
    }

    /**
     * 删除指定前缀的Key,返回删除的数量
     */
    public static long delWithPrefix(String prefix) {
        if (null == prefix || "".equals(prefix)) {
            throw new NullPointerException();
        }

        ByteSequence prefixByte = bytesOf(prefix);

        DeleteOption deleteOption = DeleteOption.newBuilder().withPrefix(prefixByte).build();

        long deleted = 0;
        try {
            deleted = kvClient.delete(prefixByte, deleteOption).get(TIME_OUT, TIME_UNIT).getDeleted();
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
        return deleted;
    }

    /**
     * 开启事务进行批量增删改操作(发布/回滚操作一定要开启事务执行批量操作)
     */
    public static boolean operationWithTxn(List<String> delKeys, Map<String, String> addOrUpdateKV, String keyPrefix) {
        Txn txn = kvClient.txn();
        if (!CollectionUtils.isEmpty(delKeys)) {
            List<Op.DeleteOp> delOps = new ArrayList<>();
            delKeys.forEach(item -> {

                ByteSequence bsKey = bytesOf(keyPrefix.concat("/").concat(item));
                Op.DeleteOp delOp = Op.delete(bsKey, DeleteOption.DEFAULT);

                delOps.add(delOp);
            });
            txn.Then(delOps.toArray(new Op.DeleteOp[0]));
        }
        if (!CollectionUtils.isEmpty(addOrUpdateKV)) {
            Set<Map.Entry<String, String>> entries = addOrUpdateKV.entrySet();
            List<Op.PutOp> addOrUpdateOps = new ArrayList<>();
            for (Map.Entry<String, String> item : entries) {
                ByteSequence bsKey = bytesOf(keyPrefix.concat("/").concat(item.getKey()));
                ByteSequence bsVal = bytesOf(item.getValue());
                Op.PutOp putOp = Op.put(bsKey, bsVal, PutOption.DEFAULT);

                addOrUpdateOps.add(putOp);
            }

            txn.Then(addOrUpdateOps.toArray(new Op.PutOp[0]));
        }
        TxnResponse txnResponse = null;
        try {
            txnResponse = txn.commit().get(TIME_OUT, TIME_UNIT);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }

        return txnResponse.isSucceeded();

    }

    /**
     * 获取锁
     *
     * @param lockName        锁名称
     * @param lockTimeoutInSecond 锁超时时间
     * @return java.lang.Long
     * @throws
     * @version 1.0
     * @date 2021/10/28
     * @since jdk1.8
     */
    public static LockData acquireLock(String lockName, long lockTimeoutInSecond) {
//        return 1L;
        Long leaseId = 0L;
        // 创建一个租约，租约有效期为ttlOfLease
        try {
            leaseId = leaseClient.grant(lockTimeoutInSecond).get(TIME_OUT, TimeUnit.MILLISECONDS).getID();
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("操作ETCD超时", e);
        }
        // 执行加锁操作，并为锁对应的key绑定租约
        try {
            LockResponse lockResponse = lockClient.lock(bytesOf(lockName), leaseId).get(TIME_OUT, TimeUnit.MILLISECONDS);
            String lockId = toString(lockResponse.getKey());
            return new LockData(lockId, leaseId);
        } catch (InterruptedException | ExecutionException e) {
            revokeLease(leaseId);
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            revokeLease(leaseId);
            return null;
        }
    }



    /**
     * 释放锁
     *
     * @param lockId 锁标记
     * @param leaseId  租约ID
     * @return void
     * @throws
     * @version 1.0
     * @date 2021/10/28
     * @since jdk1.8
     */
    public static boolean releaseLock(String lockId, Long leaseId) {
//        return true;
        try {
            // 释放锁
            UnlockResponse unlockResponse = lockClient.unlock(bytesOf(lockId)).get(TIME_OUT, TimeUnit.MILLISECONDS);
            if (unlockResponse == null) {
                return false;
            }
            // 删除租约
            if (null != leaseId) {
                revokeLease(leaseId);
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            revokeLease(leaseId);
            throw new EtcdClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            revokeLease(leaseId);
            throw new EtcdClientException("操作ETCD超时", e);
        }
    }

    @Data
    @AllArgsConstructor
    public static class LockData {
        private String lockKey;
        private long leaseId;
    }

}