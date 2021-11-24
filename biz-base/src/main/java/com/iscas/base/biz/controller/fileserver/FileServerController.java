package com.iscas.base.biz.controller.fileserver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iscas.base.biz.service.fileserver.FileServerService;
import com.iscas.datasong.lib.request.SearchDataRequest;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * 默认文件上传处理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/9/25 15:24
 * @since jdk1.8
 */
@RestController
@RequestMapping("/file")
@Api(description="文件上传")
public class FileServerController extends BaseController {
    @Autowired
    private FileServerService fileServerService;

    @ApiOperation(value="[文件服务/xxx]文件上传", notes="create by:朱全文 2019-09-25")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "files", value = "上传的文件(RequestBody),支持多个文件，文件的key为file", required = true, dataType = "MultipartFile[]")
            }
    )
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile[] files) throws BaseException {
        ResponseEntity response = getResponse();
        if (ArrayUtils.isEmpty(files)) {
            throw new BaseException("上传的数据为空");
        }
        try {
            Map<String, String> result = fileServerService.upload(files);
            response.setValue(result);
        } catch (IOException e) {
            throw new BaseException("文件上传出错", e);
        }
        return response;
    }

    @ApiOperation(value="[文件服务/xxx]文件下载", notes="create by:朱全文 2019-09-25")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "path", value = "下载的文件路径(RequestParam)", required = true, dataType = "String")
            }
    )
    @GetMapping("/download")
    public void download(@RequestParam("path") String path) throws BaseException {
        if (StringUtils.isEmpty(path)) {
            throw new BaseException("文件路径为空");
        }
        fileServerService.download(path);
    }


    @RequestMapping(value = "/docx/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void saveWord(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            String body = "";
            try {
                Scanner scanner = new Scanner(request.getInputStream());
                scanner.useDelimiter("\\A");
                body = scanner.hasNext() ? scanner.next() : "";
                scanner.close();
            } catch (Exception ex) {
                writer.write("get request.getInputStream error:" + ex.getMessage());
                return;
            }
            if (body.isEmpty()) {
                writer.write("empty request.getInputStream");
                return;
            }
            JSONObject jsonObj = JSON.parseObject(body);
            int status = (Integer) jsonObj.get("status");
            int saved = 0;
            if(status == 2 || status == 3) //MustSave, Corrupted
            {
                String downloadUri = (String) jsonObj.get("url");
                try {
                    URL url = new URL(downloadUri);
                    java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                    InputStream stream = connection.getInputStream();
                    if (stream == null) {
                        throw new Exception("Stream is null");
                    }
                    String path = request.getParameter("path");
                    File savedFile = new File("d:tempx/" + path);
                    try (FileOutputStream out = new FileOutputStream(savedFile)) {
                        int read;
                        final byte[] bytes = new byte[1024];
                        while ((read = stream.read(bytes)) != -1) {
                            out.write(bytes, 0, read);
                        }
                        out.flush();
                    }
                    connection.disconnect();
                } catch (Exception ex) {
                    saved = 1;
                    ex.printStackTrace();
                }
            }
            writer.write("{\"error\":" + saved + "}");
        } catch (IOException e) {
            writer.write("{\"error\":-1}");
            e.printStackTrace();
        }
    }
}
