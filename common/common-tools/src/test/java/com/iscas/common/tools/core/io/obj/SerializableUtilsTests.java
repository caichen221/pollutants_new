package com.iscas.common.tools.core.io.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.Serializable;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/7/12 14:47
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class SerializableUtilsTests {
    static class A implements Serializable {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public A(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "A{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 测试序列化,反序列化
     * */
    @Test
    public void test() throws IOException, ClassNotFoundException {
        A a = new A("zhangsan", 18);
        byte[] serialize = SerializableUtils.serialize(a);
        A newA = SerializableUtils.deserialize(serialize);
        System.out.println(a);
        System.out.println(newA);

    }
}
