package com.iscas.sso.mix.ticket.share.agent;

import com.iscas.common.rpc.tools.grpc.client.GrpcClientUtils;
import com.iscas.sso.mix.ticket.share.agent.model.ResponseEntity;
import com.iscas.sso.mix.ticket.share.agent.model.TicketEntity;
import com.iscas.sso.mix.ticket.share.agent.service.TicketGrpc;
import io.grpc.ManagedChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 13:22
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class GrpcClientTests {
    @Test
    public void test() {
        //客户端调用
        ManagedChannel managedChannel = GrpcClientUtils.getManagedChannel("127.0.0.1", 8564);
        TicketGrpc.TicketBlockingStub ticketBlockingStub = TicketGrpc.newBlockingStub(managedChannel);
        TicketEntity.TkEntity tkEntity = TicketEntity.TkEntity.newBuilder().setDomain("aaaa")
                .setExpire(11)
                .setTicket("1111").build();
        ResponseEntity.ResEntity resEntity = ticketBlockingStub.storeTicket(tkEntity);
        TicketEntity.TkEntity tkEntity2 = TicketEntity.TkEntity.newBuilder().build();
        ResponseEntity.ResEntity ticket = ticketBlockingStub.getTicket(tkEntity2);
        System.out.println(ticket);
        System.out.println(ticket.getMessage());
        managedChannel.shutdown();
    }
}
