package com.iscas.base.biz.util.echarts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参考：https://www.jianshu.com/p/dfc28fd7d786
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/24 21:00
 * @since jdk1.8
 */
public class EchartsUtils {
    private static final String SUCCESS_CODE = "1";

    public static String generateEchartsBase64(String phantomjsUrl, String option) throws ClientProtocolException, IOException {
        String base64 = "";
        if (option == null) {
            return base64;
        }
        option = option.replaceAll("\\s+", "").replaceAll("\"", "'");

        // 将option字符串作为参数发送给echartsConvert服务器
        Map<String, String> params = new HashMap<>();
        params.put("opt", option);
        String response = HttpUtil.post(phantomjsUrl, params, "utf-8");

        // 解析echartsConvert响应
        JSONObject responseJson = JSON.parseObject(response);
        String code = responseJson.getString("code");

        // 如果echartsConvert正常返回
        if (SUCCESS_CODE.equals(code)) {
            base64 = responseJson.getString("data");
        }
        // 未正常返回
        else {
            String string = responseJson.getString("msg");
            throw new RuntimeException(string);
        }
        return base64;
    }
}
