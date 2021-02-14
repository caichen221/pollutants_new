package com.iscas.sso.mix.ticket.share.agent.service;

import com.iscas.sso.mix.ticket.share.agent.model.ResponseEntity;
import com.iscas.sso.mix.ticket.share.agent.model.TicketEntity;
import io.grpc.stub.StreamObserver;

/**
 * 票据实现类
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 11:10
 * @since jdk1.8
 */
public class TicketServiceImpl extends TicketGrpc.TicketImplBase {

    @Override
    public void storeTicket(TicketEntity.TkEntity request, StreamObserver<ResponseEntity.ResEntity> responseObserver) {
        ResponseEntity.ResEntity resEntity = ResponseEntity.ResEntity.newBuilder().setMessage("111").build();
        responseObserver.onNext(resEntity);
        responseObserver.onCompleted();
    }

}
