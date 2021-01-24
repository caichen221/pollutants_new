package com.iscas.biz.jersey.resource;

import com.iscas.templet.common.ResponseEntity;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 17:38
 * @since jdk1.8
 */
@Path("/demo")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class DemoResource {

    @Path("/get")
    @GET
    public ResponseEntity get() {
        return new ResponseEntity();
    }

    //匹配路径:ip:port/jersey/demo/get/(任意整数)
    @Path("/get/{id}")
    @GET
    public ResponseEntity getById(@PathParam("id")Integer id){
        ResponseEntity response = new ResponseEntity();
        response.setValue(id);
        return response;
    }

    //匹配路径正则表达式
    @GET
    @Path("get/regex/{str:[0-9]+}")
    public ResponseEntity getUseRegex(@PathParam("str")String str){
        ResponseEntity response = new ResponseEntity();
        response.setValue(str);
        return response;
    }


    //？后的参数
    @GET
    @Path("get/param")
    public ResponseEntity getParam(@QueryParam("str") String str){
        ResponseEntity response = new ResponseEntity();
        response.setValue(str);
        return response;
    }

    //header里的参数
    @Path("/get/header/param")
    @GET
    public ResponseEntity headerParam(@HeaderParam("header") String header){
        ResponseEntity response = new ResponseEntity();
        response.setValue(header);
        return response;
    }

    //cookie里参数
    @Path("/get/cookie/param")
    @GET
    public ResponseEntity cookieParam(@CookieParam("cookie") String cookie){
        ResponseEntity response = new ResponseEntity();
        response.setValue(cookie);
        return response;
    }

    //获取request/response
    @Path("/get/req/res")
    @GET
    public ResponseEntity getReqRes(@Context HttpServletRequest req, @Context HttpServletResponse res){
        ResponseEntity response = new ResponseEntity();
        System.out.println(req);
        System.out.println(res);
        return response;
    }

    //获取form表单的内容
    @Path("/post/formParam")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ResponseEntity formParam(@FormParam("name") String name){
        ResponseEntity response = new ResponseEntity();
        response.setValue(name);
        return response;
    }

    /**
     * 获取上传文件输入流
     */
    @Path("/post/formDataParam")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity formDataParam(@FormDataParam("file") InputStream inputStream) throws IOException {
        System.out.println(inputStream);
        return new ResponseEntity();
    }

    //上传表单，转为bean
    @Path("/post/beanParam")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ResponseEntity beanParam(@BeanParam TestModel testModel){
        System.out.println(testModel);
        return new ResponseEntity();
    }

    //上传表单，转为bean
    @Path("/put/jsonvalue")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity jsonValue(TestModel testModel){
        System.out.println(testModel);
        return new ResponseEntity();
    }



    static class TestModel{
        @FormParam("name")
        private String name;
        @FormParam("age")
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }


}
