package com.iscas.biz.controller.common;

import com.iscas.biz.service.common.SystemLogService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * 系统日志控制器
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/2/25 14:30
 * @since jdk1.8
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
@Api(tags = "系统日志")
@RestController
@RequestMapping("/system/log")
@Validated
public class SystemLogController extends BaseController {
    private final SystemLogService systemLogService;

    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping("/tree")
    @ApiOperation(value="获取日志树-create by zqw 2021-02-25")
    public TreeResponse getTree() throws BaseException {
        return systemLogService.getTree();
    }

    @ApiOperation(value="获取日志-create by zqw 2021-02-25")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "filePath", value = "日志文件路径，在树上可以获取", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "lines", value = "行数", required = true, dataType = "Integer")
            }
    )
    @GetMapping("/view")
    public ResponseEntity viewLog(@NotBlank(message = "日志文件路径不能为空") String filePath, @Min(value = 1, message = "行数必须大于0") int lines) throws BaseException {
        try {
            return getResponse().setValue(systemLogService.viewLog(filePath, lines));
        } catch (IOException e) {
            throw new BaseException("读取日志数据出错", e);
        }
    }

}
