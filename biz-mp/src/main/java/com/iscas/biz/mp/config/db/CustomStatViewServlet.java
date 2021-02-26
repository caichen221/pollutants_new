package com.iscas.biz.mp.config.db;

import cn.hutool.core.io.FileUtil;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/22 18:04
 * @since jdk1.8
 */
public class CustomStatViewServlet extends StatViewServlet {

    @Override
    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response) throws ServletException, IOException {
        String filePath = getFilePath(fileName);

        if (filePath.endsWith(".html")) {
            response.setContentType("text/html; charset=utf-8");
        }
        if (fileName.endsWith(".jpg")) {
            byte[] bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }
            return;
        }

        String text = Utils.readFromResource(filePath);
        if (text == null) {
            response.sendRedirect(uri + "/index.html");
            return;
        }
        if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        //********定制一些信息*********

        String content = "";
        if (fileName.endsWith("common.js")) {
            try {
                ClassPathResource classPathResource = new ClassPathResource("static/common.js");
                File file = classPathResource.getFile();
                content = FileUtil.readUtf8String(file);
            }catch (Exception e){

            }
            if (StringUtils.isNotBlank(content)){
                text = content;
            }
            //去除footer中banner等信息
            //text = text.replaceAll(".*this.buildFooter().*", "");
            //修改顶部Druid Monitor链接
            //text.replaceAll(".*get\\('header.html',function\\(html\\).*", "");
        }
        response.getWriter().write(text);
    }
}
