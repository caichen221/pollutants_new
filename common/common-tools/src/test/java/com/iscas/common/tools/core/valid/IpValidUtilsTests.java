package com.iscas.common.tools.core.valid;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/9/20 8:35
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class IpValidUtilsTests {

    @Test
    public void test() {
        String flag1 = IpValidUtils.validIp4Or6("10.2.3.125");
        Assert.assertEquals("IPv4", flag1);

        String flag2 = IpValidUtils.validIp4Or6("1000.2.3.125");
        Assert.assertEquals("Neither", flag2);

        String flag3 = IpValidUtils.validIp4Or6("FF01::101");
        Assert.assertEquals(flag3, "IPv6");
    }
}
