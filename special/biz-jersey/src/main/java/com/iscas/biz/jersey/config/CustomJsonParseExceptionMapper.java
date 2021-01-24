package com.iscas.biz.jersey.config;
 
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iscas.templet.common.ResponseEntity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
 

public class CustomJsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
    @Override
    public Response toResponse(JsonParseException exception) {
        ResponseEntity response = new ResponseEntity<>();
        response.setMessage("json转换出错");
        response.setDesc(exception.getMessage());
        return Response
                .status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
    }
}