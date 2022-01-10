package com.iscas.biz.controller.common.file;

import com.iscas.biz.domain.common.FileInfo;
import com.iscas.biz.service.common.FileInfoService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件服务控制器-带断点续传
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/10 14:38
 * @since jdk1.8
 */
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FragmentFileServerController extends BaseController {
    private final FileInfoService fileInfoService;

    /**
     * 上传
     *
     * @param file
     * @param suffix
     * @param shardIndex
     * @param shardSize
     * @param shardTotal
     * @param size
     * @param key
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/upload")
    public ResponseEntity upload(MultipartFile file, String suffix, Integer shardIndex, Integer shardSize,
                                 Integer shardTotal, Integer size, String key, String name) {
        try {
            fileInfoService.upload(file, suffix, shardIndex, shardSize, shardTotal, size, key, name);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return getResponse();
    }

    @PostMapping("/check")
    public ResponseEntity check(String key) throws BaseException {
        List<FileInfo> check = fileInfoService.check(key);
        //如果这个key存在的话 那么就获取上一个分片去继续上传
        if (check.size() != 0) {
            return getResponse().setValue(check.get(0));
        }
        return getResponse().setStatus(500).setValue("no sliver");
    }

}
