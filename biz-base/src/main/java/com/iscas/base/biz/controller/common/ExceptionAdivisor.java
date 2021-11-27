package com.iscas.base.biz.controller.common;

import com.iscas.base.biz.config.Constants;
import com.iscas.base.biz.config.StaticInfo;
import com.iscas.base.biz.config.cors.CorsProps;
import com.iscas.base.biz.util.AuthContextHolder;
import com.iscas.common.tools.assertion.AssertRuntimeException;
import com.iscas.common.tools.exception.ExceptionUtils;
import com.iscas.templet.exception.*;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.tools.core.random.RandomStringUtils;
import com.iscas.templet.common.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestControllerAdvice
@Component
@Slf4j
public class ExceptionAdivisor implements Constants {
    @Autowired
    private CorsProps corsProps;
    @Value("${exception-stack-trace-max-size:500}")
    private int exceptionStackTraceMaxSize;


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity to400(MethodArgumentNotValidException e){
        StringBuilder result = new StringBuilder();
        result.append("error 400 :");
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        if(errors != null){
            for (FieldError error: errors) {
                String field = error.getField();
                String msg = error.getDefaultMessage();
                // 这里可以使用field 和msg 组合成返回的内容，这里就是做一个拼接
                result.append(field).append(",").append(msg).append(";");
            }
        }
        ResponseEntity res = res(HttpStatus.BAD_REQUEST.value(), result.toString(), e);
        res.setDesc(result.toString());
        return res;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity to400(ConstraintViolationException e){
        StringBuilder result = new StringBuilder();
        result.append("error 400 :");
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        if(cvs != null){
            for (ConstraintViolation cv: cvs) {
                Path path = cv.getPropertyPath();
                String msg = cv.getMessage();
                result/*.append(path).append(",")*/.append(msg).append(";");

            }
        }
        ResponseEntity res = res(HttpStatus.BAD_REQUEST.value(), result.toString(), e);
        res.setDesc(result.toString());
        return res;
    }

    //将ValidDataException的HTTP状态码改为400
    @ExceptionHandler(value = ValidDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to400(ValidDataException e){
        return res(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity loginException(LoginException e){
        HttpSession session = SpringUtils.getSession();
        String data = RandomStringUtils.randomStr(16);
        session.setAttribute(SESSION_LOGIN_KEY, data);
        ResponseEntity res = res(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), e);
        res.setValue(data);
        return res;
    }

    @ExceptionHandler(value = AuthenticationRuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity loginRuntimeException(AuthenticationRuntimeException e){
        HttpSession session = SpringUtils.getSession();
        String data = RandomStringUtils.randomStr(16);
        session.setAttribute(SESSION_LOGIN_KEY, data);
        ResponseEntity res = res(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), e);
        res.setValue(data);
        return res;
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity to403Exception(AuthorizationException e){
        return res(HttpStatus.FORBIDDEN.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = AuthorizationRuntimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity to403Exception(AuthorizationRuntimeException e){
        return res(HttpStatus.FORBIDDEN.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = RequestTimeoutRuntimeException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity to408(BaseRuntimeException e){
        return res(HttpStatus.REQUEST_TIMEOUT.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to500(BaseException e){
        return res(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = BaseRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to500(BaseRuntimeException e){
        return res(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
    }

    @ExceptionHandler(value = AssertRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to500(AssertRuntimeException e){
        return res(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to500(MaxUploadSizeExceededException e){
        return res(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传文件大小超过限制", e);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity to500(Throwable throwable){
        return res(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误", throwable);
    }

    /**
     * 生成异常
     * */
    public ResponseEntity res(int status, String msg, Throwable e) {
        log.error("异常", e);
        ResponseEntity responseEntity = new ResponseEntity(status, msg);
        responseEntity.setDesc(getMessage(e));
        responseEntity.setStackTrace(ExceptionUtils.getExceptionStackTrace(e, exceptionStackTraceMaxSize));
        setResponseCros();
        setResponseInfo(responseEntity);
        AuthContextHolder.removeContext();
        return responseEntity;
    }

    /**设置跨域*/
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private void setResponseCros(){
        HttpServletRequest request = SpringUtils.getRequest();
        HttpServletResponse response = SpringUtils.getResponse();
        String origin = request.getHeader("Origin");
        if (origin == null || "null".equals(origin)) {
            origin = corsProps.getOrigin();
        }
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, corsProps.getCredentials());//服务器同意客户端发送cookies
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, corsProps.getMethods());
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, corsProps.getHeaders());

    }



    /**
     * 递归获取异常的message
     * */
    private String getMessage(Throwable throwable){
        String message = null;
        if(throwable != null){
            String message1 = throwable.getMessage();
            if(message1 != null){
                return message1;
            }else{
                Throwable cause = throwable.getCause();
                return getMessage(cause);
            }
        }
        return message;
    }

    /**
     * 设置耗时等信息
     * */
    private void setResponseInfo(ResponseEntity responseEntity){
        HttpServletRequest request = SpringUtils.getRequest();
        Long start = null;
        try {
            start = StaticInfo.START_TIME_THREAD_LOCAL.get();
        } finally {
            StaticInfo.START_TIME_THREAD_LOCAL.remove();
        }
        if (Objects.nonNull(start)) {
            responseEntity.setTookInMillis(System.currentTimeMillis() - start);
        }
        if(request != null){
            String requestURI = request.getRequestURI();
            responseEntity.setRequestURL(requestURI);
        }
    }


}
