package com.iscas.sso.mix.auth.service;

import com.auth0.jwt.interfaces.Claim;
import com.iscas.sso.mix.auth.exception.LoginUserPwdErrorException;
import com.iscas.sso.mix.auth.model.ResponseEntity;
import com.iscas.sso.mix.auth.model.UserEntity;
import com.iscas.sso.mix.auth.util.JWTUtils;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidTokenException;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 12:44
 * @since jdk1.8
 */
public class AuthServiceImpl extends AuthGrpc.AuthImplBase {
    private AuthCommonService authCommonService;

    public AuthCommonService getAuthCommonService() {
        if (authCommonService == null) {
            synchronized (AuthServiceImpl.class) {
                if (authCommonService == null) {
                    authCommonService = new AuthCommonService();
                }
            }
        }
        return authCommonService;
    }

    @Override
    public void login(UserEntity.User request, StreamObserver<ResponseEntity.ResEntity> responseObserver) {
        ResponseEntity.ResEntity resEntity = null;
        try {
            String token = getAuthCommonService().login(request.getUsername(), request.getPassword());
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(200).setValue(token).build();
        } catch (LoginUserPwdErrorException e) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(602).setMessage("用户名或密码错误").build();
        } catch (Throwable e) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(500).setMessage("登录出错").setDesc(e.getMessage()).build();
        }
        responseObserver.onNext(resEntity);
        responseObserver.onCompleted();
    }

    @Override
    public void verify(UserEntity.User request, StreamObserver<ResponseEntity.ResEntity> responseObserver) {
        ResponseEntity.ResEntity resEntity = null;
        try {
            Map<String, Claim> stringClaimMap = JWTUtils.verifyToken(request.getToken());
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(200).setValue(stringClaimMap.get("username").asString()).build();
        } catch (ValidTokenException e) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(601).setMessage("token校验出错").build();
        } catch (Throwable e) {
            resEntity = ResponseEntity.ResEntity.newBuilder().setStatus(500).setMessage("token校验出错").setDesc(e.getMessage()).build();
        }
        responseObserver.onNext(resEntity);
        responseObserver.onCompleted();
    }
}
