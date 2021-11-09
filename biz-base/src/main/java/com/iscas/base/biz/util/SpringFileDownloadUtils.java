package com.iscas.base.biz.util;

import com.iscas.common.tools.office.excel.ExcelUtils;
import com.iscas.common.tools.office.excel.FlowExcelDataProducer;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import com.iscas.templet.exception.BaseRuntimeException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 使用spring的方式下载文件
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/19 21:25
 * @since jdk1.8
 */
public class SpringFileDownloadUtils {
    private SpringFileDownloadUtils() {}

    /**
     * 根据输入流和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param is 输入流
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, InputStream is) throws UnsupportedEncodingException {
        InputStreamResource isr = new InputStreamResource(is);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", FileDownloadUtils.getContentDispositionVal(SpringUtils.getRequest(),fileName))
                .body(isr);
    }

    /**
     * 根据文件和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param file 文件
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, File file) throws FileNotFoundException, UnsupportedEncodingException {
        return download(fileName, new FileInputStream(file));
    }


    /**
     * 根据文件路径和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param filePath 文件路径
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        return download(fileName, new FileInputStream(filePath));
    }

    /**
     * 根据字节数组和文件名下载文件， 接口的返回值需要为ResponseEntity<ByteArrayResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param bytes 字节数组
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.ByteArrayResource>
     */
    public static ResponseEntity<ByteArrayResource> download(String fileName, byte[] bytes) throws UnsupportedEncodingException {
        ByteArrayResource bar = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", FileDownloadUtils.getContentDispositionVal(SpringUtils.getRequest(), fileName))
                .body(bar);
    }

    /**
     * 根据字节数组和文件名下载文件， 接口的返回值需要为ResponseEntity<ByteArrayResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param sheetNames Excel的sheet页名字
     * @param flowExcelDataProducer 流式生成数据的回调，会一直调用{@link FlowExcelDataProducer#supply(int, String)}，
     *                              直至返回为空,第一个参数为调用的次数，从1开始，第二个参数为sheet页的名字
     *
     * @throws Exception
     * @return void
     */
    public static void createAndDownloadExcel(String fileName, List<String> sheetNames, FlowExcelDataProducer flowExcelDataProducer) throws Throwable {
        if (!fileName.endsWith(".xlsx")) {
            throw new BaseRuntimeException("仅支持xlsx格式的文件");
        }
        HttpServletRequest request = SpringUtils.getRequest();
        HttpServletResponse response = SpringUtils.getResponse();
        FileDownloadUtils.setResponseHeader(request, response, fileName);
        ExcelUtils.flowExportXLSXExcel(sheetNames, response.getOutputStream(), flowExcelDataProducer);
    }

}
