package com.iscas.base.biz.config.norepeat.submit;

import com.iscas.base.biz.aop.enable.EnableNoRepeatSubmit;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;



/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 21:43
 * @since jdk1.8
 */
public class NoRepeatSubmitLockTypeImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), NoRepeatSubmitLockTypeImportSelector.class);
//        Assert.state(annType != null, "Unresolvable type argument for AdviceModeImportSelector");
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableNoRepeatSubmit.class.getName(), false));
//        if (attributes == null) {
//            throw new IllegalArgumentException(String.format(
//                    "@%s is not present on importing class '%s' as expected",
//                    annType.getSimpleName(), importingClassMetadata.getClassName()));
//        }

        NoRepeatSubmitLockType lockType = attributes.getEnum("lockType");
        switch (lockType) {
            case NONE: {
                return new String[] {NoRepeatSubmitNoneConfig.class.getName()};
            }
            case JVM: {
                return new String[] {NoRepeatSubmitJVMConfig.class.getName()};
            }
            case REDIS: {
                return new String[] {NoRepeatSubmitRedisConfig.class.getName()};
            }
            default: {
                return new String[] {NoRepeatSubmitNoneConfig.class.getName()};
            }
        }

    }
}
