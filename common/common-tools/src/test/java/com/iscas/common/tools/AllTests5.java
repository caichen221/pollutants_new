package com.iscas.common.tools;

import com.iscas.common.tools.arithmetic.FloatExactArithUtilsTests;
import com.iscas.common.tools.assertion.AssertArrayUtilsTests;
import com.iscas.common.tools.assertion.AssertObjUtilsTests;
import com.iscas.common.tools.captcha.CaptchaTests;
import com.iscas.common.tools.core.classloader.JarClassloaderTests;
import com.iscas.common.tools.core.collection.CollectionRaiseUtilsTests;
import com.iscas.common.tools.core.collection.MapRaiseUtilsTests;
import com.iscas.common.tools.core.date.DateRaiseUtilsTests;
import com.iscas.common.tools.core.date.DateSafeUtilsTests;
import com.iscas.common.tools.core.io.file.*;
import com.iscas.common.tools.core.io.rar.RarUtilsTests;
import com.iscas.common.tools.core.io.serial.JdkSerializableUtilsTests;
import com.iscas.common.tools.core.io.serial.KryoSerializerTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import java.io.File;


/**
 *  Junit5所有的单元测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/28 21:21
 * @since jdk1.8
 */


@RunWith(JUnitPlatform.class)
@SelectClasses({
        FileUtilsTests.class, FloatExactArithUtilsTests.class,
        AssertArrayUtilsTests.class, AssertObjUtilsTests.class,
        CaptchaTests.class, JarClassloaderTests.class,
        CollectionRaiseUtilsTests.class, MapRaiseUtilsTests.class,
        DateRaiseUtilsTests.class, DateSafeUtilsTests.class,
        FileTypeUtilsTests.class, FileUtilsTests.class,
        JarPathUtilsTests.class, LargeFileTests.class,
        ThumbnailPicUtilsTests.class, RarUtilsTests.class,
        JdkSerializableUtilsTests.class, KryoSerializerTests.class

})
public class AllTests5 {
}
