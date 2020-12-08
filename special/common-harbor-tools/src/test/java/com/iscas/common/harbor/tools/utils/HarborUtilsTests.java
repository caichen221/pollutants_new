package com.iscas.common.harbor.tools.utils;

import com.iscas.common.harbor.tools.exception.CallHarborException;
import com.iscas.common.harbor.tools.model.ModuleHealth;
import com.iscas.common.harbor.tools.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/8 18:43
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class HarborUtilsTests {

    /**
     * 查看组件健康状况
     * */
    @Test
    public void test1() throws IOException, CallHarborException {
        List<ModuleHealth> healths = HarborUtils.health();
        healths.forEach(System.out::println);
    }

    /**
     * 查询project
     * */
    @Test
    public void test2() throws IOException, CallHarborException {
        List<Project> lib = HarborUtils.search("lib");
        lib.forEach(System.out::println);
    }

}
