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

}
