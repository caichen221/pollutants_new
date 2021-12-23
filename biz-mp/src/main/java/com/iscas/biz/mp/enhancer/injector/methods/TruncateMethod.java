package com.iscas.biz.mp.enhancer.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * truncate表
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/23 13:57
 * @since jdk1.8
 */
public class TruncateMethod extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        return null;
    }
}
