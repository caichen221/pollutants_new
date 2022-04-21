package com.iscas.common.k8s.tools.util;

import com.iscas.common.k8s.tools.cfg.K8sConstants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 通用工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2019/12/2 15:00
 * @since jdk1.8
 */
@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class CommonUtils {

    private CommonUtils() {}

    /**
     * 获取某个时间点距离当前的时间,并作一些处理
     * */
    public static String getTimeDistance(Date date) {
        if (date == null) {
            return null;
        }
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

    public static Date timeOffset(Date time) {
        return time == null ? null : new Date(time.getTime() + K8sConstants.TIME_OFFSET);
    }

}
