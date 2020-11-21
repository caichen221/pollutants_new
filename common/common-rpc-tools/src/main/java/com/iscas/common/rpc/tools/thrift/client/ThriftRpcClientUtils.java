package com.iscas.common.rpc.tools.thrift.client;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * thrift客户端工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/21 13:02
 * @since jdk1.8
 */
public class ThriftRpcClientUtils {
    private ThriftRpcClientUtils() {}

    /**
     * 获取transport
     * 使用例子：
     *        TTransport transport = null;
     *         try {
     *             transport = new TSocket("127.0.0.1", 6367, 2000);
     *             // 协议要和服务端一致
     *             TProtocol protocol = new TBinaryProtocol(transport);
     *
     *             RPCDateService.Client client = new RPCDateService.Client(protocol);
     *             transport.open();
     *             String result = client.getDate("张三");
     *             System.out.println("Thrify client result =: " + result);
     *         } catch (TTransportException e) {
     *             e.printStackTrace();
     *         } catch (TException e) {
     *             e.printStackTrace();
     *         } finally {
     *             if (null != transport) {
     *                 transport.close();
     *             }
     *         }
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/21
     * @param ip
     * @param port
     * @param timeout 超时时间
     * @throws
     * @return
     */
    public static TTransport getTransport(String ip, int port, long timeout) {
        TTransport transport = new TSocket("127.0.0.1", 6367, 2000);
        return transport;
    }

    public static void close(TTransport tTransport) {
        tTransport.close();
    }
}
