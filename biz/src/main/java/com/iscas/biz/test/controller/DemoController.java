package com.iscas.biz.test.controller;

import cn.hutool.core.io.IoUtil;
import com.iscas.base.biz.util.SpringUtils;
import com.iscas.common.web.tools.cookie.CookieUtils;
import com.iscas.common.web.tools.json.JsonObject;
import com.iscas.common.web.tools.json.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/7/3 8:25
 * @since jdk1.8
 */
@RestController
@Api(tags = "swagger示例")
public class DemoController {
    @ApiOperation(value="[测试/李爽] 测试测试哈哈", notes="create by:朱全文 2020-02-21")
    @GetMapping("/tx")
    public String tx() {
        CookieUtils.setCookie(SpringUtils.getResponse(), "mykey", "myvalue", 10000);
        System.out.println("tx");
        return "tx";
    }

    @ApiOperation(value="[测试/李爽] 测试测试哈哈", notes="create by:朱全文 2020-02-21")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "map", value = "上传到数据", required = true, dataType = "TableSearchRequest")
            }
    )
    @PostMapping("/tx2")
    public String tx(@RequestBody Map<String, Object> map) {
        return "tx";
    }

    @PutMapping("/tx3")
    public String tx(@RequestParam String key, @RequestBody String data) {
        System.out.println(data);
        return "tx";
    }
    @DeleteMapping("/tx4")
    public String tx4(@RequestParam String key) {
        return "tx";
    }

    @PostMapping("/tx5")
    public String tx5(@RequestParam(required = false) String name, @RequestParam(required = false) String age) {
        System.out.println(name);
        System.out.println(age);
        return "tx";
    }

    @GetMapping("/tx6")
    public void tx6(HttpServletResponse response) throws IOException {
        File file = new File("e:/A2019003_1km_tsm.L3M.hdf");
        @Cleanup InputStream is = new FileInputStream(file);
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8"); // 改成输出excel文件
        response.setHeader(
                "Content-disposition",
                "attachment; filename="
                        +"source.hdf");
        IoUtil.copy(is, response.getOutputStream());
    }

    @PostMapping("/tx7")
    public String tx6(MultipartFile file) throws IOException {
       return "tx7";
    }

    @GetMapping("/tx8")
    public String tx8() throws IOException {
        SpringUtils.getSession().setAttribute("name", "张三");
        return "tx7";
    }

    @GetMapping(value = "/video", produces = {"video/mp4"})
    public void tx9() throws IOException {
        byte[] bytes = null;
        File file = new File("H:\\ideaProjects\\newframe-dev\\biz\\src\\main\\resources\\static\\三国之战神无双.mp4");
        @Cleanup RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        HttpServletRequest request = SpringUtils.getRequest();
        String range = request.getHeader("Range");
        int from = 0;
        int to = accessFile.length() - 1 > 10240 ? 10240 : (int) accessFile.length() - 1;
        if (range != null) {
            range = StringUtils.substringAfter(range, "bytes=");
            String fromStr = StringUtils.substringBefore(range, "-");
            String toStr = StringUtils.substringAfterLast(range, "-");
            if (StringUtils.isNotEmpty(fromStr)) {
                from = Integer.valueOf(fromStr);
            }
            if (from != 0) {
                to = accessFile.length() - 1 > from + to ? from + to : (int) accessFile.length() - 1;
            }
            if (StringUtils.isNotEmpty(toStr)) {
                to = Integer.valueOf(toStr);
            }
        }

        accessFile.seek(from);
        bytes = new byte[to - from + 1];
        accessFile.read(bytes);
        HttpServletResponse response = SpringUtils.getResponse();
        response.setHeader("Content-Type", "video/mp4");
        response.setHeader("Content-Range", "bytes " + from + "-" + to + "/" + accessFile.length());
        response.setStatus(206);
        response.getOutputStream().write(bytes);
    }

    @GetMapping("/health")
    public String health() {
        JsonObject jsonObject = JsonUtils.createJsonObject();
        jsonObject.set("health", 1)
                  .set("version","0.0.1")
                  .set("cache_refresh", 1);
        return jsonObject.toJson();
    }
}
