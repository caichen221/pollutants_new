package com.iscas.biz.mp.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author admin
 */
@SuppressWarnings("rawtypes")
public class IdGeneratorUtils {
    private IdGeneratorUtils() {}
    public static String generator(Class clazz, String primaryKey) {
        try {
            Field declaredField = clazz.getDeclaredField(primaryKey);
            boolean annotationPresent = declaredField.isAnnotationPresent(TableId.class);
            if (annotationPresent) {
                TableId annotation = declaredField.getAnnotation(TableId.class);
                IdType type = annotation.type();
                if (Objects.equals(type.name(), IdType.ASSIGN_ID.name())) {
                    return IdWorker.getIdStr();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
