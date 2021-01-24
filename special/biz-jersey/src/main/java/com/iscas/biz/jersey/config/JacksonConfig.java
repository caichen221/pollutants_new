package com.iscas.biz.jersey.config;
 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
 
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
 
/**
 * JSON序列化
 * */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
 
    private static ObjectMapper mapper = new ObjectMapper();
 
    static{
        //允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        //允许内容中含有注释符号/* 或 //
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS,true);
        //允许没有引号的属性名字
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        //设置timeZone
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
 
        //序列化配置
        //不包含null的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
 
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,true);
 
        //自身引用时报错
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES,true);
 
        //反序列化配置
        //不允许json含有类不包含的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
 
    }
 
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
 
}