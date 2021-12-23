package com.iscas.biz.mp.util;

import com.iscas.biz.mp.enhancer.mapper.DynamicMapper;
import com.iscas.templet.exception.ValidDataException;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/20 22:28
 * @since jdk1.8
 */
public class ValidatePropDistinctUtils {
    private ValidatePropDistinctUtils() {}

    public static Map validate(DynamicMapper dynamicMapper, String tableName, String colName, Object val) {
        String sql = "select count(*) as c from %s where %s = '%s'";
        sql = String.format(sql, tableName, colName, val.toString());
        Map map = dynamicMapper.selectOne(sql);
        return map;
    }

    public static void validateFromMysql(DynamicMapper dynamicMapper, String tableName, String colName, Object val) throws ValidDataException {
        Map map = validate(dynamicMapper, tableName, colName, val);
        if (!Objects.equals(map.get("c"), 0L)) {
            throw new ValidDataException(String.format("表[%s]的列[%s]的值：[%s]不能重复", tableName, colName, val.toString()));
        }
    }
}
