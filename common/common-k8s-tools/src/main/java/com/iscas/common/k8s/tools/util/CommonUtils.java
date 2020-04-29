package com.iscas.common.k8s.tools.util;

import com.iscas.common.tools.core.date.DateSafeUtils;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 通用工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/2 15:00
 * @since jdk1.8
 */
public class CommonUtils {
    private CommonUtils() {}

    /**
     * 获取某个时间点距离当前的时间,并作一些处理
     * */
    public static String getTimeDistance(Date date) {
        long start = date.getTime();
        long end = System.currentTimeMillis();
        long distance = end - start;
        if (distance < TimeUnit.MINUTES.toMillis(1)) {
            long l = Math.round(distance/1000);
            return "约" + l + "秒";
        } else if (distance >= TimeUnit.MINUTES.toMillis(1)
                    && distance < TimeUnit.HOURS.toMillis(1)) {
            long l = Math.round(distance/(1000 * 60));
            return "约" + l + "分钟";
        } else if (distance >= TimeUnit.HOURS.toMillis(1)
                && distance < TimeUnit.DAYS.toMillis(1)) {
            long l = Math.round(distance/(1000 * 60 * 60));
            return "约" + l + "小时";
        } else {
            long l = Math.round(distance/(1000 * 60 * 60 * 24));
            return "约" + l + "天";
        }
    }

}
