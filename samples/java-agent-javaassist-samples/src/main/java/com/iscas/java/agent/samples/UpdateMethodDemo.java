package com.iscas.java.agent.samples;

import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>修改方法的DEMO<p/>
 * <p>注意的是无法让局部变量在两个部分公用，因为添加了{}<p/>
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/21 20:38
 * @since jdk1.8
 */
public class UpdateMethodDemo {

    public static class DemoClass {
        public String randonNum(int len) {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < len; i++) {
                res.append(threadLocalRandom.nextInt(10));
            }
            return res.toString();
        }
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassPool classPool = new ClassPool(true);
        String targetClassName = "com.iscas.java.agent.samples.UpdateMethodDemo$DemoClass";
        CtClass ctClass = classPool.get(targetClassName);
        CtClass intClass = classPool.get("int");

        //获取randonNum函数
        CtMethod randonNumMethod = ctClass.getDeclaredMethod("randonNum", new CtClass[]{intClass});
        //复制函数，并改名
        CtMethod agentRandomNumMethod = CtNewMethod.copy(randonNumMethod, randonNumMethod.getName() + "$agent", ctClass, null);
        //新函数注入class
        ctClass.addMethod(agentRandomNumMethod);
        String methodBody = "{" +
                "long begin = System.nanoTime();" +
                "String str = randonNum$agent($1);" +
                "long end = System.nanoTime();" +
                "System.err.println(\"函数耗时：\" + (end - begin) + \"纳秒\");" +
                "return ($r)str;" +
                "}";
        //重写原来函数的代码并调用复制的函数
        randonNumMethod.setBody(methodBody);

        //调用
        DemoClass demoClass = (DemoClass) ctClass.toClass().getDeclaredConstructor().newInstance();
        String s = demoClass.randonNum(100);
        System.out.println(s);

        //实例化一个新对象也可以
        String s1 = new DemoClass().randonNum(10);
        System.out.println(s1);

    }
}
