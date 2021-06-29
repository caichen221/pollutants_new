package com.iscas.pap.pluggable;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/27 16:36
 * @since jdk1.8
 */
@SupportedAnnotationTypes(value = {"com.iscas.pap.pluggable.annotation.ThrowBaseException",
        "com.iscas.pap.pluggable.annotation.TestData"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class CustomAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("begin");
        for (TypeElement annotation : annotations) {
            if (annotation.getSimpleName().contentEquals("TestData")) {
                Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
                for (Element element : elementsAnnotatedWith) {
                    String pkg = element.getEnclosingElement().toString();
                    String className = element.getSimpleName().toString();

                    try {
                        rewriteClass(pkg, className);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1:" + pkg);
                    System.out.println("2:" + className);
                }
            }
            System.out.println("anno:" + annotation);
        }
        System.out.println(roundEnv);
        return true;
    }

    private void rewriteClass(String pkg, String className) throws IOException {
        final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(pkg + "." + "TestPap2");
//        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(pkg + ".Test");
//        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(pkg + "." + className);
        try (Writer writer = sourceFile.openWriter()) {
            writer.write("package " + pkg + ";\n");
            writer.write("public class TestPap2 {\n");
            writer.write("public int a = 5;\n");
            writer.write("}");
        }
    }

}
