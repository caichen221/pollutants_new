package com.iscas.common.tools.core.reflect;

import com.iscas.common.tools.core.reflect.reflectTest.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.*;

/**
 * 增强反射工具类测试
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2018/7/16
 * @since jdk1.8
 */
public class ReflectUtilsTests {
    private static A a = null;

    //初始化测试数据
    private static A11 getA11(){
        SecureRandom random = new SecureRandom();
        A11 a11x = new A11();
        a11x.setData(new float[]{3,4,5.6f,4.4545f,random.nextFloat()});
        Map map1 = new HashMap<>();
        map1.put("wge","ewg" + UUID.randomUUID());
        map1.put("weg",null);
        a11x.setMap(map1);
        a11x.setX1("weg");
        a11x.setX2(232.3346 + random.nextDouble());
        a11x.setX4(34643.45777f);
        return a11x;
    }
    private static A2 getA2(){
        SecureRandom random = new SecureRandom();
        A2 a2 = new A2();
        a2.setX1(22 + random.nextInt(12));
        a2.setX2(235.2f);
        return a2;
    }
    static {
        a = new A();
        A1 a1 = new A1();
        a1.setA11List(Arrays.asList(getA11(),getA11(),getA11()));
        a.setA1(a1);
        a.setA2List(Arrays.asList(getA2(), getA2()));
        Map map1 = new HashMap();
        map1.put("qe",getA2());
        map1.put("wewg", getA2());
        a.setMap(map1);
    }

    /**
     * 通过反射获取某个对象的某个方法
     * */
    @Test
    public void test1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("--------反射测试1 begin---------");
        int hash = (int) ReflectUtils.doMethod(a, "getA1Hash");
        System.out.println(hash);
        System.out.println("--------反射测试1 end---------");
    }

    /**
     * 通过反射获取某个对象的某个方法，并执行携带参数
     * */
    @Test
    public void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("--------反射测试2 begin---------");
        String result = (String) ReflectUtils.doMethodWithParam(a, "xxx","x",34,new float[]{3,5,6,5.6f,56.78f});
        System.out.println(result);
        System.out.println("--------反射测试2 end---------");
    }

    /**判断一个类是不是基本数据类型*/
    @Test
    public void test3() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("--------反射测试3 begin---------");
        Integer a = new Integer(4);
        boolean flag = ReflectUtils.isWrapClass(a.getClass());
        Assertions.assertEquals(flag, true);
        boolean flag1 = ReflectUtils.isWrapClass(A.class);
        Assertions.assertEquals(flag1, false);
        System.out.println("--------反射测试3 end---------");
    }

    /**
     * 获取一个类和其父类的所有属性
     * */
    @Test
    public void test4(){
        System.out.println("--------反射测试4 begin---------");
        List<Field> fields = ReflectUtils.findAllFieldsOfSelfAndSuperClass(A1111.class);
        System.out.println(fields);
        System.out.println("--------反射测试4 end---------");
    }

}
