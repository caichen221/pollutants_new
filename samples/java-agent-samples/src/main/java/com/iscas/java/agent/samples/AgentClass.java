package com.iscas.java.agent.samples;

import java.lang.instrument.Instrumentation;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/25 21:18
 * @since jdk1.8
 */
public class AgentClass {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("java agent---premain");
        inst.addTransformer(new MyClassFileTransformer());
    }
}
