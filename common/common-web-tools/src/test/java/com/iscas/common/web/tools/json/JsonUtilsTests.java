package com.iscas.common.web.tools.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/4 8:37
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class JsonUtilsTests {
    private String json = "{\n" +
                            "\t\"name\": \"zhangsan\",\n" +
                            "\t\"age\": 24,\n" +
                            "\t\"friends\": [{\n" +
                            "\t\t\"name\":\"lisi\",\n" +
                            "\t\t\"age\": 18\n" +
                            "\t}]\n" +
                            "}";

    private String json2 = "{\n" +
            "\t\"json\": {\n" +
            "\t\t\"a\": {\n" +
            "\t\t\t\"www\": \"ff\",\n" +
            "\t\t\t\"rrr\": [\"v1\", \"v2\"]\n" +
            "\t\t},\n" +
            "\t\t\"b\": {\n" +
            "\t\t\t\"www\": \"4567ttt\",\n" +
            "\t\t\t\"rrr\": [\"v1\", \"v2\"]\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    @Test
    public void test0() {
        Map map = JsonUtils.fromJson(json2, Map.class);
        System.out.println(map);
    }

    @Test
    public void test1() {
        String name = JsonUtils.getValueByKey(json, "name");
        System.out.println(name);
    }
    @Test
    public void test2() {
        String ret = JsonUtils.getValueByKey(json2, "json.b.www");
        System.out.println(ret);
    }

    /**
     * 测试嵌套的类
     * */
    @Test
    public void test3() throws JsonProcessingException {
        A<SubA> a = new A<>();
        SubA subA = new SubA();
        subA.setName("张三");
        subA.setDesc("啦啦啦");
        a.setT(subA);
        a.setStr("cgwegwegweg");

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(a);
        System.out.printf("JSON字符串：%s\n", s);

        JsonUtils.ParametricTypes parametricTypes = new JsonUtils.ParametricTypes();
        parametricTypes.setClazz(A.class);

        JsonUtils.ParametricTypes subParametricTypes = new JsonUtils.ParametricTypes();
        subParametricTypes.setClazz(SubA.class);

        parametricTypes.setSubClazz(Arrays.asList(subParametricTypes));

        A o = JsonUtils.fromJson(s, parametricTypes);
        System.out.println(o);
    }

    /**
     * 测试嵌套的类
     * */
    @Test
    public void test4() throws JsonProcessingException {
        A<SubA> a = new A<>();
        SubA subA = new SubA();
        subA.setName("张三");
        subA.setDesc("啦啦啦");
        a.setT(subA);
        a.setStr("cgwegwegweg");

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(a);
        System.out.printf("JSON字符串：%s\n", s);


        A o = JsonUtils.fromJson(s, A.class, SubA.class);
        System.out.println(o);
    }

    static class A<T> {
        private T t;
        private String str;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
    static class SubA {
        private String name;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
