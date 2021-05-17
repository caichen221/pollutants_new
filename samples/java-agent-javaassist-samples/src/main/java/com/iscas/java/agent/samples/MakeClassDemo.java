package com.iscas.java.agent.samples;

import javassist.*;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/26 21:10
 * @since jdk1.8
 */
public class MakeClassDemo {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //获取ClassPool
        ClassPool classPool = new ClassPool(true);
        classPool.insertClassPath(new LoaderClassPath(MakeClassDemo.class.getClassLoader()));

        //创建一个类，这里使用了一个接口，也可以不用
        CtClass testMakeClass = classPool.makeClass("com.iscas.java.agent.samples.TestMakeClass");
        //添加接口
        testMakeClass.addInterface(classPool.get(ITestMakeClass.class.getName()));
        //返回值
        CtClass returnClass = classPool.get(String.class.getName());
        //方法名
        String methodName = "sayHello";
        //方法参数
        CtClass[] parameters = new CtClass[]{classPool.get(String.class.getName())};
        //创建方法
        CtMethod method = new CtMethod(returnClass, methodName, parameters, testMakeClass);
        //创建方法体
        method.setBody("{\n" +
                "        System.out.println(\"this is sayHello, parameter is \" + $1);\n" +
                "        System.out.println($type);\n" +
                "        System.out.println($args);\n" +
                "        System.out.println($class);\n" +
                "        return $1;\n" +
                "    }");
        //将方法添加到类中
        testMakeClass.addMethod(method);
        Class<?> aClass = testMakeClass.toClass();
        //创建类
        ITestMakeClass tmc = (ITestMakeClass) aClass.getDeclaredConstructor().newInstance();
        String result = tmc.sayHello("小哥哥");
        System.out.println(result);
    }

    public interface ITestMakeClass {
        String sayHello(String helloStr);
    }

    String sayHello(String helloStr) {
        System.out.println("this is sayHello, parameter is " + helloStr);
        return helloStr;
    }

}
