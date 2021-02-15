//package com.iscas.sso.mix.ticket.share.agent.model;
//
//import lombok.Data;
//
///**
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2021/2/14 11:22
// * @since jdk1.8
// */
//@Data
//public class HttpResponseEntity<T> {
//    /**
//     * http状态码
//     */
//    protected Integer status;
//    /**
//     * 状态信息
//     */
//    protected String message;
//
//    /**
//     * 服务器内部错误描述
//     */
//    protected String desc;
//
//    /**
//     * 返回值
//     */
//    protected T value;
//
//    /**
//     * 访问URL
//     */
//    protected String requestURL;
//
//    protected long tookInMillis;
//
//    protected int total;
//
//    public HttpResponseEntity(Integer status, String message) {
//        super();
////        this.status = status;
//        this.message = message;
//    }
//
//    public HttpResponseEntity() {
//        super();
////        this.status = 200;
//        this.message = "操作成功";
//    }
//    public HttpResponseEntity(String message){
//        super();
//        this.message = message;
//    }
//}
