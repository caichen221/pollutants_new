package com.iscas.sso.mix.ticket.share.agent.handler;

import com.iscas.sso.mix.ticket.share.agent.model.CommonTicketEntity;
import com.iscas.sso.mix.ticket.share.agent.service.TicketCommonService;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/5 22:07
 * @since jdk1.8
 */
public class TicketHandler {
    private TicketCommonService ticketCommonService;

    public TicketHandler(TicketCommonService ticketCommonService) {
        this.ticketCommonService = ticketCommonService;
    }

    public ServerResponse storeTicket(ServerRequest request) throws BaseException {
        try {
            CommonTicketEntity commonTicketEntity = request.body(CommonTicketEntity.class);
            if (StringUtils.isEmpty(commonTicketEntity.getTicket())) {
                throw new BaseException("ticket不能为空");
            }
            ticketCommonService.storeTicket(commonTicketEntity);
            ResponseEntity res = new ResponseEntity<>();
            res.setMessage("存储成功");
            return ServerResponse.ok().body(res);
        } catch (ServletException e) {
            throw new BaseException("存储ticket出错", e);
        } catch (IOException e) {
            throw new BaseException("存储ticket出错", e);
        }
    }

    public ServerResponse getTicket(ServerRequest request) throws BaseException {
        Optional<String> domain = request.param("domain");
        if (domain.isEmpty()) {
            domain = Optional.of("default");
        }
        String ticket = ticketCommonService.getTicket(domain.get());
        ResponseEntity<String> res = new ResponseEntity<>();
        res.setValue(ticket);
        return ServerResponse.ok().body(res);
    }
}
