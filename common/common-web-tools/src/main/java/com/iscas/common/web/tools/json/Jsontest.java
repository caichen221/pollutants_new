package com.iscas.common.web.tools.json;

import cn.miludeer.jsoncode.JsonCode;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/4 9:02
 * @since jdk1.8
 */
public class Jsontest {
    private static String json = "{\"json\":{\"a\":{\"www\":\"ff\",\"rrr\":[\"v1\",\"v2\"]},\"b\":{\"www\":\"4567ttt\",\"rrr\":[\"v1\",\"v2\"]}}}";



    public static String usejsoncode() {   // 使用jsoncode
        String ret = JsonCode.getValue(json, "$.json.b.www");
        return ret;
    }

    public static void main(String[] argv) {
        long time1 = System.currentTimeMillis();
        for(int i=0; i<10000000;i++) {
            usejsoncode();
        }
        long time2 = System.currentTimeMillis();

        System.out.println("jsoncode:" + (time2 - time1));
    }
}

