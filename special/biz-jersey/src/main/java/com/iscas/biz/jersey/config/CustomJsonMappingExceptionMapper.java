package com.iscas.biz.jersey.config;
 
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iscas.biz.jersey.exception.CustomExceptionMapper;
import com.iscas.templet.common.ResponseEntity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
 

//@Provider
public class CustomJsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
 
    @Override
    public Response toResponse(JsonMappingException exception) {
        ResponseEntity response = new ResponseEntity<>();
        response.setMessage("json转换出错");
        response.setDesc(exception.getMessage());
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
    }
}