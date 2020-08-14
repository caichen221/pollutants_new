package com.iscas.base.biz.service.fileserver;

import com.iscas.base.biz.util.MultipartFileUtils;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.tools.core.random.RandomStringUtils;
import com.iscas.common.web.tools.file.FileDownloadUtils;
import com.iscas.templet.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/9/25 15:52
 * @since jdk1.8
 */
@Service
public class DefaultFileServerService implements FileServerService{
    @Value("${file.server.path}")
    private String fileServerPath;

    /**
     * 文件上传处理，返回文件的key以及对应的文件存储路径
     * */
    @Override
    public Map<String, String> upload(MultipartFile[] files) throws IOException {
        Map<String, String> result = new HashMap<>(2 << 2);
        //获取文件存储的根路径
        File path = getPath();
        for (MultipartFile multipartFile: files) {
            String key = multipartFile.getOriginalFilename();
            File file = new File(path, key);
            if (!file.exists()) {
                file.createNewFile();
            }
            MultipartFileUtils.copy(multipartFile, file);
            String absolutePath = file.getAbsolutePath();
            //将路径中的\\替换为/
            absolutePath = absolutePath.replaceAll("\\\\", "/");
            result.put(key, absolutePath);
        }
        return result;
    }

//    public static void main(String[] args) {
//        System.out.println("F:\\fileserver\\2019-09-25\\hsvVn3nX\\pod hook.txt".replace("\\", "/"));
//    }

    @Override
    public void download(String path) throws BaseException {
        File file = new File(path);
        if (! file.exists()) {
            throw new BaseException("下载的文件不存在或已删除");
        }
        try {
            FileDownloadUtils.downFile(SpringUtils.getRequest(), SpringUtils.getResponse(), path, file.getName());
        } catch (Exception e) {
            throw new BaseException("文件下载出错", e);
        }
    }


    private File getPath() {
        File pfile = new File(fileServerPath);
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String timeStr = now.format(dtf);
        File file = new File(pfile, timeStr);
        if (!file.exists()) {
            file.mkdirs();
        }
        String randomStr = RandomStringUtils.randomStr(8);
        file = new File(file, randomStr);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
