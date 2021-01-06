package com.iscas.common.tools.core.arithmetic;

import java.math.BigDecimal;

/**
 *
 * 数学运算扩展函数
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/23 17:30
 * @since jdk1.8
 */
public class MathUtils {

    /**
     * 为浮点型数据取有效数字
     * @version 1.0
     * @since jdk1.8
     * @date 2020/12/23
     * @param data 数据
     * @param scale 保留有效数字
     * @throws
     * @return double
     */
    public static double scale(double data, int scale) {
        BigDecimal bg = new BigDecimal(data);
        return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
