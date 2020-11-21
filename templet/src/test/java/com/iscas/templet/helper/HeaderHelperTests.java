package com.iscas.templet.helper;

import com.iscas.templet.exception.HeaderException;
import com.iscas.templet.view.table.TableHeaderResponseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/8/24 21:53
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class HeaderHelperTests {

    @Test
    public void test() throws HeaderException {
        TableHeaderResponseData headerResponseData = HeaderHelper.convertToHeader(AnnotationHeaderTestBean.class);
        System.out.println(headerResponseData);
    }


}
