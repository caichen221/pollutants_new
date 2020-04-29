package com.iscas.common.tools.core.io.file;

import com.iscas.common.tools.core.convert.BytesConvertUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;

/**
 * 判断一个文件类型的工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/8/6 8:48
 * @since jdk1.8
 */
public class FileTypeUtils {
    private static int FILE_PREFIX_LENGTH = 15;
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 判断一个文件的类型
     * */
    public static FileTypeEnum getFileType(InputStream is) throws IOException {
        //读取FILE_PREFIX_LENGTH个字节，少于FILE_PREFIX_LENGTH，后面都是0
        int length = FILE_PREFIX_LENGTH;
        byte[] b = new byte[length];
        is.read(b);
        String prefix = bytesToHexString(b);
        FileTypeEnum[] values = FileTypeEnum.values();
        if (ArrayUtils.isNotEmpty(values)) {
            for (FileTypeEnum fileTypeEnum : values) {
                String enumPrefix = fileTypeEnum.getPrefix();
                if (prefix.toLowerCase().startsWith(enumPrefix.toLowerCase())) {
                    return fileTypeEnum;
                }
            }
        }
        return FileTypeEnum.UNKOWN;
    }

    /**
     * 判断一个文件的类型
     * */
    public static FileTypeEnum getFileType(String path) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return getFileType(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


}
