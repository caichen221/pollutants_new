package com.iscas.sso.mix.ticket.share.agent;

import com.iscas.common.rpc.tools.grpc.server.GrpcServerUtils;
import com.iscas.sso.mix.ticket.share.agent.service.TicketServiceImpl;
import com.iscas.sso.mix.ticket.share.agent.util.ConfigUtils;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 启动类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 11:13
 * @since jdk1.8
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        TicketServiceImpl ticketService = new TicketServiceImpl();
        Server server = GrpcServerUtils.start(ticketService, Integer.valueOf(ConfigUtils.readProp("ticket.share.server.port")));
        log.info("========ticket共享服务已启动===========");
        server.awaitTermination();
    }

//    /**0
//     * 测试使用grpc发布服务，并调用
//     * */
//    @Test
//    public void testCall() throws IOException, InterruptedException {
//        PersonServiceImpl psi = new PersonServiceImpl();
//        Server server = GrpcServerUtils.start(psi, 8888);
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            //客户端调用
//            ManagedChannel managedChannel = GrpcClientUtils.getManagedChannel("127.0.0.1", 8888);
//            PersonServiceGrpc.PersonServiceBlockingStub psbs = PersonServiceGrpc.newBlockingStub(managedChannel);
//            MyRequest req = MyRequest.newBuilder().setUsername("张三").build();
//            MyResponse res = psbs.getRealNameByUsername(req);
//            System.out.println(res.getRealname());
//            Assert.assertEquals("真实姓名:张三", res.getRealname());
//            server.shutdownNow();
//        }).start();
//        server.awaitTermination();
//    }
}
