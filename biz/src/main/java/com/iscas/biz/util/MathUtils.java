package com.iscas.biz.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
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

    public static String double2Percent(double data, int scale) {
        String pattern = getPercentPattern(scale);
        if (Double.isNaN(data) || Double.isInfinite(data)) {
            return pattern;
        }
        DecimalFormat df = new DecimalFormat(pattern);
        BigDecimal bg = new BigDecimal(data);
        return df.format(bg);
    }

    public static double percent2Double(String percent, int scale) {

        percent = percent.replace("%", "");
        Double value = Double.valueOf(percent) / 100;
        return scale(value, scale);
    }

    private static String getStringPattern(int scale) {
        StringBuilder b = new StringBuilder("0");
        if (scale > 0) {
            b.append(".").append("0");
            for (int i = 1; i < scale; i++) {
                b.append("0");
            }
        }
        return b.toString();
    }

    private static String getPercentPattern(int scale) {
        String pattern = getStringPattern(scale);
        return pattern + "%";
    }

}
