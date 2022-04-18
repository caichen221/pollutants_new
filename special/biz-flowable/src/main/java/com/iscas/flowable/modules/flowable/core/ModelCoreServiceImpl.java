package com.iscas.flowable.modules.flowable.core;

import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:31
 * @since jdk11
 */
@Service("modelCoreService")
@Primary
public class ModelCoreServiceImpl extends ModelServiceImpl {
    //TODO  自定义流程属性 需要重新实现相关方法
}
