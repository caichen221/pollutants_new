package com.iscas.biz.jersey.filter;
 
import com.iscas.templet.common.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
 
/**
 * request过滤器
 * */
@Provider
public class CustomRequestFilter implements ContainerRequestFilter {
    @Context
    private HttpServletRequest request;
 
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println(request);
        System.out.println("requestFilter");
        String stop = requestContext.getHeaderString("stop");
        if(stop != null){
            ResponseEntity response = new ResponseEntity();
            response.setMessage("不允许的请求头：stop");
            response.setDesc("不允许的请求头：stop");
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(response).build());
        }

    }
}