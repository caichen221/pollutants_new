package com.iscas.common.rpc.tools.grpc.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * grpc 服务端工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/20 17:56
 * @since jdk1.8
 */
@Slf4j
public class GrpcServerUtils {
    private GrpcServerUtils(){}

    /**
     * 服务发布
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param entity 实体
     * @param port 发布的端口
     * @throws
     * @return void
     */
    public static Server start(BindableService entity, int port) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port).addService(entity).build().start();
        String msg = String.format("grpc服务:[%s]已发布", entity.getClass().getName());
        log.info(msg);
        if (!log.isInfoEnabled()) {
            System.out.println(msg);
        }
        return server;
    }

    /**
     * 务启动之后就会立刻停止,awaitTermination会保持服务一直运行
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param server 服务
     * @throws
     * @return void
     */
    public static void awaitTermination(Server server) throws InterruptedException {
        server.awaitTermination();
    }

    /**
     * shutdown
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param server 服务
     * @throws
     * @return void
     */
    public static void shutdown(Server server) throws InterruptedException {
        server.shutdown();
    }

    /**
     * 立即shutdown
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/20
     * @param server 服务
     * @throws
     * @return void
     */
    public static void shutdownNow(Server server) throws InterruptedException {
        server.shutdownNow();
    }
}
