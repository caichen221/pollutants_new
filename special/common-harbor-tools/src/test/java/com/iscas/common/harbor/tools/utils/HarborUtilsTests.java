package com.iscas.common.harbor.tools.utils;

import com.iscas.common.harbor.tools.HarborUtils;
import com.iscas.common.harbor.tools.exception.CallHarborException;
import com.iscas.common.harbor.tools.model.ModuleHealth;
import com.iscas.common.harbor.tools.model.Project;
import com.iscas.common.harbor.tools.model.Repository;
import com.iscas.common.harbor.tools.model.Tag;
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
        List<Project> lib = HarborUtils.searchProject("lib");
        lib.forEach(System.out::println);
    }

    /**
     * 创建一个project
     * */
    @Test
    public void test3() throws IOException, CallHarborException {
        Project project = new Project();
        project.setName("xxx")
                .setProjectPublic(true)
                .setRepoCount(100);
        HarborUtils.createProject(project);
    }

    /**
     * 查询repository
     * */
    @Test
    public void test4() throws IOException, CallHarborException {
        List<Repository> lib = HarborUtils.searchRepo("lib");
        lib.forEach(System.out::println);
    }

    /**
     * 按照ID 获取repository的属性
     * */
    @Test
    public void test5() throws IOException, CallHarborException {
        Project project = HarborUtils.getProjectById(2);
        System.out.println(project);
    }

    /**
     * 根据镜像名称获取tag
     * */
    @Test
    public void test6() throws IOException, CallHarborException {
        List<Tag> tags = HarborUtils.getTags("library/consumer-test");
        tags.forEach(System.out::println);
    }


}
