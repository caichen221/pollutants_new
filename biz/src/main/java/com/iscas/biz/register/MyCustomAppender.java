//package com.iscas.biz.register;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.iscas.log.LogBaseEntity;
//import com.iscas.log.appender.CustomAppender;
//import lombok.Data;
//
//import java.util.Map;
//
///**
// * //TODO
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2019/4/1 15:27
// * @since jdk1.8
// */
//@Data
//@Deprecated
//public class MyCustomAppender extends CustomAppender {
//    private CustomConfig customConfig;
//
//
//    @Override
//    public void saveToDb() {
//        try {
//            LogBaseEntity logBaseEntity = super.getBaseInfo();
//            MyLogEntity myLogEntity = new MyLogEntity();
//            //拷贝logBaseEntity 到自定义的Log对象
//            BeanUtil.copyProperties(logBaseEntity, myLogEntity);
//            String appName = customConfig.getAppName();
//            myLogEntity.setAppName(appName);
//            Map params = myLogEntity.getParams();
//            String modelName = (String) params.get("模块名");
//            myLogEntity.setModelName(modelName);
//
//            //TODO 存储MyLogEntity
//
//
//        } catch (InterruptedException var2) {
//            var2.printStackTrace();
//        }
//    }
//}
