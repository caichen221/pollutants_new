package com.iscas.common.tools.core.math;

import java.math.BigDecimal;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/23 17:30
 * @since jdk1.8
 */
public class MathUtils {

    public static double scale(double data, int scale) {
        BigDecimal bg = new BigDecimal(data);
        return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
