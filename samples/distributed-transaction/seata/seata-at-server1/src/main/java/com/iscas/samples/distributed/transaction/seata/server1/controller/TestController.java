package com.iscas.samples.distributed.transaction.seata.server1.controller;

import com.iscas.samples.distributed.transaction.seata.server1.mapper.UserMapper;
import com.iscas.samples.distributed.transaction.seata.server1.po.User;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/24 22:59
 * @since jdk1.8
 */
@RestController
@RequestMapping("/server1")
public class TestController {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserMapper userMapper;
    @GlobalTransactional(name = "my-test-transactional", timeoutMills = 600000)
    @GetMapping
    public String test1() throws IOException {
        System.out.println(dataSource);
        User user = new User();
        user.setName("zhangsan");
        userMapper.insert(user);

        call();
        call();

        return "success";
    }

    private void call() throws IOException {
        String xid = RootContext.getXID();
        System.out.println("xid:" + xid);
        URL url = new URL("http://localhost:7002/server2?id=1&xid=" + xid);
        // 打开连接 获取连接对象
        URLConnection connection = url.openConnection();
        connection.setRequestProperty(RootContext.KEY_XID, xid);

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
    }
}
