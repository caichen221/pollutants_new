package com.iscas.common.tools.gis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 距离测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/17 15:32
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class LatLonUtilsTests {

    /**
     * 测试获取经纬度之间的距离
     * */
    @Test
    public void test1() {
        double distance = LatLonUtils.getDistance(30, 126, 45, 118);
        System.out.println(distance);
    }
}
