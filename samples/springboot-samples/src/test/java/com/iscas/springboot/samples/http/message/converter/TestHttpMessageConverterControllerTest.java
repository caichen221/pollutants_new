package com.iscas.springboot.samples.http.message.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscas.springboot.samples.jackson.type1.Model;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 20:03
 * @since jdk1.8
 */
public class TestHttpMessageConverterControllerTest {
    @Test
    public void testReadWrite() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ConverterTestModel converterTestModel = ConverterTestModel.builder()
                .id(1)
                .age(12)
                .name("dada")
                .createTime(new Date()).build();
        byte[] bytes = objectMapper.writeValueAsBytes(converterTestModel);

        URL url = new URL("http://localhost:5678/test/http/message/convert/reader/writer");
        // 打开连接 获取连接对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/json+converttestmodel");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(bytes);

        // 从连接对象中获取网络连接中的输入字节流对象
        InputStream inputStream = connection.getInputStream();
        // 将输入字节流包装成输入字符流对象,并进行字符编码
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        // 创建一个输入缓冲区对象，将字符流对象传入
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 定义一个字符串变量，用来接收输入缓冲区中的每一行字符串数据
        String line;
        // 创建一个可变字符串对象，用来装载缓冲区对象的数据，使用字符串追加的方式，将响应的所有数据都保存在该对象中
        StringBuilder stringBuilder = new StringBuilder();
        // 使用循环逐行读取输入缓冲区的数据，每次循环读入一行字符串数据赋值给line字符串变量，直到读取的行为空时标识内容读取结束循环
        while ((line = bufferedReader.readLine()) != null) {
            // 将从输入缓冲区读取到的数据追加到可变字符对象中
            stringBuilder.append(line);
        }
        // 依次关闭打开的输入流
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        System.out.println(stringBuilder.toString());
    }
}