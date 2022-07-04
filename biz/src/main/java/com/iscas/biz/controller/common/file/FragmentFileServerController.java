package com.iscas.biz.controller.common.file;

import com.iscas.biz.domain.common.FileInfo;
import com.iscas.biz.service.common.FileInfoService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.Exceptions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * @version 1.0
 * @date 2022/1/10 14:38
 * @since jdk1.8
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Api(tags = "文件上传示例-支持断点续传")
public class FragmentFileServerController extends BaseController {
    private final FileInfoService fileInfoService;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "MultipartFile"),
                    @ApiImplicitParam(name = "suffix", value = "文件后缀", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "shardIndex", value = "分片索引号", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "shardSize", value = "当前上传分片大小", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "shardTotal", value = "分片数目", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "size", value = "文件大小", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "key", value = "文件的key,MD5码：文件名 + 文件大小 + 文件类型 + 文件最后修改时间的MD5码", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "name", value = "文件名称", required = true, dataType = "String")
            }
    )
    public ResponseEntity upload(MultipartFile file, String suffix, int shardIndex, int shardSize,
                                 int shardTotal, int size, String key, String name) throws BaseException {
        try {
            fileInfoService.upload(file, suffix, shardIndex, shardSize, shardTotal, size, key, name);
        } catch (IOException | InterruptedException e) {
            throw Exceptions.baseException("文件上传出错", e);
        }
        return getResponse();
    }

    @PostMapping("/check")
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "key", value = "文件的key,MD5码：文件名 + 文件大小 + 文件类型 + 文件最后修改时间的MD5码", required = true, dataType = "String")
            }
    )
    public ResponseEntity check(String key) throws BaseException {
        List<FileInfo> check = fileInfoService.check(key);
        //如果这个key存在的话 那么就获取上一个分片去继续上传
        if (check.size() != 0) {
            return getResponse().setValue(check.get(0));
        }
        return getResponse().setStatus(500).setValue("no sliver");
    }

}
