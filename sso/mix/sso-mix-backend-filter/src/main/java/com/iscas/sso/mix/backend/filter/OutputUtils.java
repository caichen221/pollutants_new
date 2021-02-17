package com.iscas.sso.mix.backend.filter;

import com.iscas.common.web.tools.json.JsonUtils;
import com.iscas.templet.common.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OutputUtils {
    private OutputUtils(){}

    public static void output(HttpServletResponse response, int status, String msg, String desc) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setHeader("Access-Control-Allow-Origin", "*");
        //服务器同意客户端发送cookies
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token,Authorization,Access-Control-Allow-Origin");
        ServletOutputStream pw = response.getOutputStream();
        ResponseEntity responseEntity = new ResponseEntity(status,msg);
        responseEntity.setDesc(desc);
        pw.write(JsonUtils.toJson(responseEntity).getBytes("UTF-8"));
        pw.flush();
//        pw.close();
    }

    public static void output(HttpServletResponse response, int status, ResponseEntity responseEntity) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setHeader("Access-Control-Allow-Origin", "*");
        //服务器同意客户端发送cookies
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token,Authorization,Access-Control-Allow-Origin");
        ServletOutputStream pw = response.getOutputStream();
        pw.write(JsonUtils.toJson(responseEntity).getBytes("UTF-8"));
        pw.flush();
//        pw.close();
    }
}