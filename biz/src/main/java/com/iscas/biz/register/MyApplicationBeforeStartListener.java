//package com.iscas.biz.register;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationPreparedEvent;
//import org.springframework.context.ApplicationListener;
//
///**
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2019/1/24 8:48
// * @since jdk1.8
// */
//@Slf4j
//public class MyApplicationBeforeStartListener implements ApplicationListener<ApplicationPreparedEvent> {
//    private boolean flag = false;
//    @Override
//    public void onApplicationEvent(ApplicationPreparedEvent event) {
////        try {
////            if (!flag) {
////                //注册第一个自定义方式，添加一个模块名参数
////                LogRegister.addStyle(new LogParam("模块名", String.class));
////                //注册第二个自定义方式，添加一个模块名参数,以及一个异常参数
////                LogRegister.addStyle(new LogParam("模块名", String.class),
////                        new LogParam("异常", Throwable.class));
////                InitConfig.loadFromResource();
////                flag = true;
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (JoranException e) {
////            e.printStackTrace();
////        }
//    }
//
//}
//
