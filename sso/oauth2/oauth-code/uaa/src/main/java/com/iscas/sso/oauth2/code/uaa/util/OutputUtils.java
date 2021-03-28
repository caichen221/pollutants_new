package com.iscas.sso.oauth2.code.uaa.util;

import com.iscas.common.web.tools.json.JsonUtils;
import com.iscas.templet.common.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 输出工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/17 17:33
 * @since jdk1.8
 */
public class OutputUtils {
    private OutputUtils(){}

    public static void output(HttpServletResponse response, int status, String msg, String desc) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
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
        ServletOutputStream pw = response.getOutputStream();
        pw.write(JsonUtils.toJson(responseEntity).getBytes("UTF-8"));
        pw.flush();
//        pw.close();
    }
}
