package com.iscas.sso.mix.ticket.share.agent.service;

import com.iscas.sso.mix.ticket.share.agent.model.CommonTicketEntity;
import com.iscas.sso.mix.ticket.share.agent.model.ResponseEntity;
import com.iscas.sso.mix.ticket.share.agent.model.TicketEntity;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 票据实现类
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 11:10
 * @since jdk1.8
 */
public class TicketServiceImpl extends TicketGrpc.TicketImplBase {
    private TicketCommonService ticketCommonService;
    public TicketCommonService getTicketCommonService() {
        if (ticketCommonService == null) {
            synchronized (TicketCommonService.class) {
                if (ticketCommonService == null) {
                    ticketCommonService = new TicketCommonService();
                }
            }
        }
        return ticketCommonService;
    }

    @Override
    public void getTicket(TicketEntity.TkEntity request, StreamObserver<ResponseEntity.ResEntity> responseObserver) {
        String domain = request.getDomain();
        if (StringUtils.isEmpty(domain)) {
            domain = "default";
        }
        String ticket = getTicketCommonService().getTicket(domain);
        ResponseEntity.ResEntity resEntity = null;
        if (ticket != null) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(200).setValue(ticket).build();
        } else {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(500).setMessage("未获取到ticket").build();
        }

        responseObserver.onNext(resEntity);
        responseObserver.onCompleted();
    }

    @Override
    public void storeTicket(TicketEntity.TkEntity request, StreamObserver<ResponseEntity.ResEntity> responseObserver) {
        String domain = request.getDomain();
        int expire = request.getExpire();
        String ticket = request.getTicket();
        ResponseEntity.ResEntity resEntity = null;
        if (StringUtils.isEmpty(ticket)) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(500).setMessage("ticket不能为空").build();
        } else {
            CommonTicketEntity commonTicketEntity = new CommonTicketEntity();
            commonTicketEntity.setTicket(ticket);
            if (StringUtils.isNotEmpty(domain)) {
                commonTicketEntity.setDomain(domain);
            }
            if (expire > 0) {
                commonTicketEntity.setExpire(expire);
            }
            getTicketCommonService().storeTicket(commonTicketEntity);
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(200).setMessage("存储成功").build();
        }
        responseObserver.onNext(resEntity);
        responseObserver.onCompleted();
    }

}
