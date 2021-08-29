package com.iscas.common.tools.core.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 健康检测工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/8/26 15:50
 * @since jdk1.8
 */
public class PingUtils {
    private PingUtils() {}


    /**
     * 查看某个服务的端口是否可连接
     * @version 1.0
     * @since jdk1.8
     * @date 2021/8/26
     * @param host 服务IP或域名
     * @param port 服务端口
     * @param timeout 超时时间(毫秒)
     * @throws
     * @return boolean
     */
    public static boolean isHostConnectable(String host, int port, int timeout) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询某个IP是否能ping通
     * @version 1.0
     * @since jdk1.8
     * @date 2021/8/26
     * @param host IP或域名
     * @param timeout 超时时间
     * @throws
     * @return boolean
     */
    public static boolean isHostReachable(String host, int timeout) {
        try {
            return InetAddress.getByName(host).isReachable(timeout);
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
        return false;
    }




}
