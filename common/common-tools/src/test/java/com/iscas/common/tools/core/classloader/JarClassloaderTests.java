package com.iscas.common.tools.core.classloader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/4/11 10:07
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class JarClassloaderTests {
//    String jar2_5 = "C:\\Users\\Administrator\\Desktop\\jar\\base2\\2.5";
    String jar2_6 = "C:\\Users\\Administrator\\Desktop\\jar\\base2\\2.6\\base2-2.6-RELEASE.jar";

    /**
     * 测试classloader，不带缓存，不从缓存读类的字节数组，也不存入缓存
     * */
    @Test
    public void test2() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        JarLoader jarLoader2 = new JarLoader(new String[]{"H:\\ideaProjects\\integration-dev\\ig-bootstrap\\src\\main\\resources" +
                "\\component\\mysqlreader.jar"});
        ClassLoaderSwapper classLoaderSwapper2 = ClassLoaderSwapper.newCurrentThreadClassLoaderSwapper();
        classLoaderSwapper2.setCurrentThreadClassLoader(jarLoader2);
        Class<?> bClass = JarLoader.outerLoadClass("com.iscas.datasong.ig.component.mysqlreader.MysqlReader");
        Object o2 = bClass.getDeclaredConstructor().newInstance();

        classLoaderSwapper2.restoreCurrentThreadClassLoader();
        Method initMethod = bClass.getDeclaredMethod("init", Map.class);
        HashMap<String, Object> params2 = new HashMap<>();
        Object invoke2 = initMethod.invoke(o2, params2);

    }

    /**
     * 测试加载mysql-reader, 带缓存
     * */
    @Test
    public void test3() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        System.out.println("=========================开始测试，读取指定jar包=========================");
        JarLoader jarLoader = new JarLoader(new String[]{"H:\\ideaProjects\\integration-dev\\ig-bootstrap\\src\\main\\resources\\" +
                "component\\mysqlreader.jar"}, true);
        ClassLoaderSwapper classLoaderSwapper = ClassLoaderSwapper.newCurrentThreadClassLoaderSwapper();
        classLoaderSwapper.setCurrentThreadClassLoader(jarLoader);
        try {
            Class<?> adClass = JarLoader.outerLoadClass("com.iscas.datasong.ig.component.mysqlreader.MysqlReader");
//        Class<?> aClass = Class.forName("com.iscas.test.base2.MyEntity");
            Object o = adClass.getDeclaredConstructor().newInstance();

            Method initMethod = adClass.getDeclaredMethod("init", Map.class);
            HashMap<String, Object> params = new HashMap<>();
            Object invoke = initMethod.invoke(o, params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            classLoaderSwapper.restoreCurrentThreadClassLoader();
        }





    }


    /**
     * 测试classloader，不带缓存，不从缓存读类的字节数组，也不存入缓存
     * */
    @Test
    public void test4() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        JarLoader jarLoader2 = new JarLoader(new String[]{"H:\\ideaProjects\\integration-dev\\ig-bootstrap\\src\\main\\resources" +
                "\\component\\csvreader.jar"});
        ClassLoaderSwapper classLoaderSwapper2 = ClassLoaderSwapper.newCurrentThreadClassLoaderSwapper();
        classLoaderSwapper2.setCurrentThreadClassLoader(jarLoader2);
        Class<?> bClass = JarLoader.outerLoadClass("com.iscas.datasong.ig.component.csvreader.CsvReader");
        Object o2 = bClass.getDeclaredConstructor().newInstance();


        Method initMethod = bClass.getDeclaredMethod("init", Map.class);
        HashMap<String, Object> params2 = new HashMap<>();
        Object invoke2 = initMethod.invoke(o2, params2);
        classLoaderSwapper2.restoreCurrentThreadClassLoader();
    }

}
