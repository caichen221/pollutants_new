package com.iscas.springboot.samples.http.message.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscas.springboot.samples.util.IOUtils;
import lombok.Cleanup;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>自定义HttpMessageConverter ConverterTestModel类型的数据转换</p>
 * <p>自定义媒体类型：</p>
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 19:20
 * @since jdk1.8
 */
public class CustomHttpMessageConverter extends AbstractHttpMessageConverter<ConverterTestModel> {

    public CustomHttpMessageConverter() {
        super(MediaType.valueOf("application/json+converttestmodel"));
        setDefaultCharset(Charset.forName("utf-8"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ConverterTestModel.class);
    }

    @Override
    protected ConverterTestModel readInternal(Class<? extends ConverterTestModel> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        //反序列化
        @Cleanup InputStream is = inputMessage.getBody();
//        @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
//        IOUtils.transferTo(is, os);
//        byte[] bytes = os.toByteArray();
//        String str = new String(bytes, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        ConverterTestModel converterTestModel = objectMapper.readValue(is, clazz);
        converterTestModel.setName("read:" + converterTestModel.getName());
        return converterTestModel;
    }

    @Override
    protected void writeInternal(ConverterTestModel converterTestModel, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //序列化
        converterTestModel.setName("write:" + converterTestModel.getName());
        OutputStream os = outputMessage.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(os, converterTestModel);
    }
}
