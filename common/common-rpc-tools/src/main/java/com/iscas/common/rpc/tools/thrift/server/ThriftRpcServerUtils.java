package com.iscas.common.rpc.tools.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.lang.reflect.InvocationTargetException;

/**
 * thrift服务端工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/21 12:47
 * @since jdk1.8
 */
public class ThriftRpcServerUtils {
    private ThriftRpcServerUtils() {}


    /**
     * 发布服务
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2020/11/21
     * @param tprocessor  TProcessor tprocessor = new RPCDateService.Processor<RPCDateService.Iface>( new RPCDateServiceImpl());
     * @param port 对外发布的端口
     * @throws
     * @return void
     */
    public static <T extends TServer> void serve(TProcessor tprocessor, int port) throws TTransportException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TServerSocket serverTransport = new TServerSocket(port);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        TServer server = new TSimpleServer(tArgs);
        server.serve();
    }
}
