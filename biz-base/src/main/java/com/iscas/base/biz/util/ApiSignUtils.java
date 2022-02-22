package com.iscas.base.biz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscas.common.web.tools.json.JsonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/2/21 14:11
 * @since jdk1.8
 */
public class ApiSignUtils {
    public static String DEFAULT_SECRET_KEY = "ISCAS123";

    private static String TIME_STAMP_KEY = "timeStamp";
    private static String SIGN_KEY = "sign";
    //超时时效，超过此时间认为签名过期
    private static Long EXPIRE_TIME = 5 * 60 * 1000L;


    /**
     * 生成签名
     */
    public static Map getSignature(Object param, String secretKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map params;
        try {
            String jsonStr = objectMapper.writeValueAsString(param);
            params = objectMapper.readValue(jsonStr, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("生成签名：转换json失败");
        }

        if (params.get(TIME_STAMP_KEY) == null) {
            params.put(TIME_STAMP_KEY, System.currentTimeMillis());
        }
        //对map参数进行排序生成参数
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        String temp = Arrays.stream(keys).map(key -> key + "=" + (!params.containsKey(key) ? "" : params.get(key)))
                .collect(Collectors.joining("&"));
        //根据参数生成签名
        String sign = DigestUtils.sha256Hex(temp + secretKey).toUpperCase();
        params.put(SIGN_KEY, sign);
        return params;
    }

    /**
     * 校验签名
     */
    public static boolean checkSignature(HttpServletRequest request, String secretKey) throws IOException {
        //获取request中的json参数转成map
        Map<String, Object> param = getAllParams(request);

        String sign = (String) param.get(SIGN_KEY);
        Long start = convertTimestamp(param.get(TIME_STAMP_KEY));
        long now = System.currentTimeMillis();
        //校验时间有效性
        if (start == null || now - start > EXPIRE_TIME || start - now > 0L) {
            return false;
        }
        //是否携带签名
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        //获取除签名外的参数
        param.remove(SIGN_KEY);
        //校验签名
        Map paramMap = getSignature(param, secretKey);
        String signature = (String) paramMap.get("sign");
        if (sign.equals(signature)) {
            return true;
        }
        return false;
    }

    private static Long convertTimestamp(Object timestampObj) {
        return timestampObj != null ? Long.parseLong(timestampObj.toString()) : null;
    }


    /**
     * 将URL的参数和body参数合并
     *
     * @param request
     * @author show
     * @date 14:24 2019/5/29
     */
    public static SortedMap<String, Object> getAllParams(HttpServletRequest request) throws IOException {

        SortedMap<String, Object> result = new TreeMap<>();
        //获取URL上的参数,并放入结果中
        result.putAll(getUrlParams(request));

        // get请求和DELETE请求不需要拿body参数
        if (!StringUtils.equalsAny(request.getMethod(), HttpMethod.GET.name(), HttpMethod.DELETE.name())) {
            Optional.ofNullable(getBodyParams(request))
                    .ifPresent(result::putAll);
        }
        return result;
    }

    /**
     * 获取 Body 参数
     *
     * @param request
     * @author show
     * @date 15:04 2019/5/30
     */
    public static Map<String, Object> getBodyParams(final HttpServletRequest request) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = "";
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }
        //转化成json对象
        return StringUtils.isNoneBlank(wholeStr) ? JsonUtils.fromJson(wholeStr.toString(), Map.class) : new HashMap<>();
    }

    /**
     * 将URL请求参数转换成Map
     */
    public static Map<String, String> getUrlParams(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString())
                .map(queryStr -> URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8))
                .map(params -> Arrays.stream(params.split("&"))
                        .collect(Collectors.toMap(s -> s.substring(0, s.indexOf("=")), s -> s.substring(s.indexOf("=") + 1))))
                .orElse(new HashMap<>());

    }


}
