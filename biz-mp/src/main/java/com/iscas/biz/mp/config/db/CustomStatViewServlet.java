package com.iscas.biz.mp.config.db;

import cn.hutool.core.io.FileUtil;
import com.alibaba.druid.support.http.StatViewServlet;
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

        //********定制一些信息*********
        String content = "";
        if (fileName.endsWith("common.js")) {
            try {
                ClassPathResource classPathResource = new ClassPathResource("static/js/common.js");
                File file = classPathResource.getFile();
                content = FileUtil.readUtf8String(file);
                response.getWriter().write(content);
                return;
            } catch (Exception e) {

            }
        }
        super.returnResourceFile(fileName, uri, response);
    }
}
