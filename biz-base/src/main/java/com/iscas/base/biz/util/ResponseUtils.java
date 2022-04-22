package com.iscas.base.biz.util;

import cn.hutool.core.io.IoUtil;
import com.iscas.common.tools.core.io.file.FileTypeEnum;
import com.iscas.common.tools.core.io.file.FileTypeUtils;
import com.iscas.templet.exception.BaseException;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/10 16:20
 * @since jdk1.8
 */
public class ResponseUtils {

    /**
     * 按流输出，设置content-type
     */
    public static void returnStream(InputStream is, HttpServletResponse response, String contentType) throws IOException {
        response.setContentType(contentType);
        IoUtil.copy(is, response.getOutputStream());
    }

    /**
     * 按流输出，按照流开头标识的文件格式自动设置content-type
     */
    public static void returnStream(InputStream is, HttpServletResponse response) throws IOException, BaseException {
        byte[] buf = new byte[FileTypeUtils.FILE_PREFIX_LENGTH];
        is.read(buf);
        String contentType = null;
        FileTypeEnum fileType = FileTypeUtils.getFileType(buf);
        switch (fileType) {
            case JPEG: {
                contentType = MediaType.IMAGE_JPEG_VALUE;
                break;
            }
            case PNG: {
                contentType = MediaType.IMAGE_PNG_VALUE;
                break;
            }
            case GIF: {
                contentType = MediaType.IMAGE_GIF_VALUE;
                break;
            }
            case XML: {
                contentType = MediaType.APPLICATION_XML_VALUE;
                break;
            }
            case HTML: {
                contentType = MediaType.TEXT_HTML_VALUE;
                break;
            }
            case ADOBE_ACROBAT: {
                contentType = MediaType.APPLICATION_PDF_VALUE;
                break;
            }
            default: {
                throw new BaseException(String.format("不支持的文件类型:%s", fileType.toString()));
            }
        }
        //现将读到的前几个字符传出去
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(buf);
        outputStream.flush();
        returnStream(is, response, contentType);
    }
}