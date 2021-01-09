package com.iscas.common.tools.gradle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * gradlemaven仓库转为maven仓库
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/8 21:28
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class GradleRepo2MavenRepoUtilsTests {
    @Test
    public void test() throws IOException {
        GradleRepo2MavenRepoUtils.gradle2Maven("D:\\soft\\gradle-repo", "d:/maven-repo");
    }
}
