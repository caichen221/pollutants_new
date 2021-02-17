package com.iscas.sso.mix.auth.handler;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.sso.mix.auth.exception.LoginUserPwdErrorException;
import com.iscas.sso.mix.auth.model.User;
import com.iscas.sso.mix.auth.service.AuthCommonService;
import com.iscas.sso.mix.auth.util.JWTUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidTokenException;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 12:47
 * @since jdk1.8
 */
public class AuthHandler {
    private AuthCommonService authCommonService;
    public AuthHandler(AuthCommonService authCommonService) {
        this.authCommonService = authCommonService;
    }
    public ServerResponse login(ServerRequest request) throws BaseException {
//        try {
//            CommonTicketEntity commonTicketEntity = request.body(CommonTicketEntity.class);
//            if (StringUtils.isEmpty(commonTicketEntity.getTicket())) {
//                throw new BaseException("ticket不能为空");
//            }
//            ticketCommonService.storeTicket(commonTicketEntity);
//            ResponseEntity res = new ResponseEntity<>();
//            res.setMessage("存储成功");
//            return ServerResponse.ok().body(res);
//        } catch (ServletException e) {
//            throw new BaseException("存储ticket出错", e);
//        } catch (IOException e) {
//            throw new BaseException("存储ticket出错", e);
//        }
        ResponseEntity res = null;
        try {
            User user = request.body(User.class);
            res = new ResponseEntity<>();
            String token = authCommonService.login(user.getUsername(), user.getPassword());
            res.setValue(token);
            return ServerResponse.ok().body(res);
        } catch (LoginUserPwdErrorException e) {
            res = new ResponseEntity();
            res.setMessage("用户名或密码错误");
            return ServerResponse.status(602).body(res);
        } catch (Throwable e) {
            throw new BaseException("登录出错", e);
        }
    }

    public ServerResponse verify(ServerRequest request) throws BaseException {
        ResponseEntity res = null;
        try {
            String token = request.headers().firstHeader("Authorization");
            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(token);
            res = new ResponseEntity<>();
            res.setValue(stringClaimMap.get("username").asString());
            return ServerResponse.ok().body(res);
        } catch (ValidTokenException e) {
            res = new ResponseEntity<>();
            res.setMessage(e.getMessage());
            res.setDesc(e.getMsgDetail());
            return ServerResponse.status(601).body(res);
        } catch (Throwable e) {
            throw new BaseException("校验token出错", e);
        }
    }
}
