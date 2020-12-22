package com.iscas.common.tools.pdfword.linux;

import java.io.IOException;

/**
 * linux下word转为pdf,需要服务器安装libreoffice7.0
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/22 17:57
 * @since jdk1.8
 */
public class LinuxWord2PDF {
    private LinuxWord2PDF() {}

    /**
     * word转为PDF，转换过程很慢
     * */
    public static int wordToPdf(String wordPath, String outDir) throws InterruptedException, IOException {
        String cmd = "libreoffice7.0 --convert-to pdf:writer_pdf_Export " + wordPath + " --outdir " + outDir;
        Process process = Runtime.getRuntime().exec(cmd);
        // 获取返回状态
        int status = process.waitFor();
        // 销毁process
        process.destroy();
        process = null;
        return status;
    }

}
