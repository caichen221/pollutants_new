package com.iscas.common.rpc.tools.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * RMI服务工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/21 18:46
 * @since jdk1.8
 */
public class RmiServerUtils {
    private RmiServerUtils() {
    }

    /**
     * RMI发布服务
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/21
     * @param host
     * @param port
     * @param publishName 发布名称/标记
     * @param entity 实现类
     * @throws
     * @return void
     */
    public static <T extends Remote> void bind(String host, int port, String publishName, T entity) throws RemoteException, AlreadyBoundException, MalformedURLException {
        LocateRegistry.createRegistry(port);
        Naming.bind("rmi://" + host + ":" + port + "/" + publishName, entity);
        System.out.println("服务已启动");

    }
}
