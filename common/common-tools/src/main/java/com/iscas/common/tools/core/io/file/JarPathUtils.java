package com.iscas.common.tools.core.io.file;

import com.iscas.common.tools.constant.CharsetConstant;
import com.iscas.common.tools.constant.FileConstant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Jar包路径工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/13
 * @since jdk1.8
 */
public class JarPathUtils {
    private JarPathUtils(){}
    /**
     * 获得jar包或者class 文件所在的位置
     * @version 1.0
     * @since jdk1.8
     * @date 2018/7/14
     * @param clazz 随便传入一个类的class对象
     * @throws UnsupportedEncodingException
     * @return java.lang.String
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String getJarPath(Class clazz) throws UnsupportedEncodingException {

        String basePath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        basePath = URLDecoder.decode(basePath, StandardCharsets.UTF_8);
        if(basePath.endsWith(FileConstant.FILENAME_SUFFIX_JAR)) {
            basePath = basePath.substring(0, basePath.lastIndexOf("/") + 1);
        }
        File f = new File(basePath);
        basePath = f.getAbsolutePath();
        return basePath;
    }

}
