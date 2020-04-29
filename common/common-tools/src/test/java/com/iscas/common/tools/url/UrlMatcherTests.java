package com.iscas.common.tools.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/7/4 17:27
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class UrlMatcherTests {
    @Test
    public void test() {
        UrlMatcher matcher = new UrlMatcher();
        String pattern = "/abc/**/a.jsp";
        System.out.println("pattern:"+pattern);
        System.out.println("/abc/aa/bb/a.jsp:"+matcher.match(pattern,"/abc/aa/bb/a.jsp"));
        System.out.println("/aBc/aa/bb/a.jsp:"+matcher.match(pattern,"/aBc/aa/bb/a.jsp"));
        System.out.println("/abc/a.jsp:"+matcher.match(pattern,"/abc/a.jsp"));
    }
}
