package com.iscas.common.tools;

import com.iscas.common.tools.core.io.file.FileUtilsTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;


/**
 *  Junit5所有的单元测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/28 21:21
 * @since jdk1.8
 */


@RunWith(JUnitPlatform.class)
@SelectClasses({FileUtilsTests.class})
public class AllTests5 {
}
