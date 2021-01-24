package com.iscas.biz.jersey.exception;

import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 全局异常处理
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        ResponseEntity response = new ResponseEntity<>();
        Response.Status status = Response.Status.OK;
        if (exception instanceof BaseException) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response.setMessage(((BaseException)exception).getMessage());
            response.setDesc(((BaseException)exception).getMsgDetail());
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response.setMessage("服务器内部错误");
            response.setDesc(exception.getMessage());
        }
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(response).build();
    }


}