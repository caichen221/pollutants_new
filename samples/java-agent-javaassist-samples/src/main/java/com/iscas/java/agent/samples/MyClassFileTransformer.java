package com.iscas.java.agent.samples;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/25 21:27
 * @since jdk1.8
 */
public class MyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String websocketConfigClass = "com/iscas/biz/config/Swagger3Config";
        if (Objects.equals(websocketConfigClass, className)) {
            try {
//                ClassPool cp = new ClassPool(true);
//                cp.insertClassPath(new LoaderClassPath(this.getClass().getClassLoader()));

                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get(className.replace("/", "."));
                System.out.println("===" + ctClass);
//                String[] params = new String[1];
//                params[0] = "org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry";
//                CtClass[] paramCtClass = classPool.get(params);
                CtMethod method = ctClass.getDeclaredMethod("api"/*, paramCtClass*/);
                System.out.println("===" + method);
                method.insertBefore("System.out.println(\"字节码前插入的处理\");");
                return ctClass.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
