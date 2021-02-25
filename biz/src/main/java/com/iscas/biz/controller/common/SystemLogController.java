package com.iscas.biz.controller.common;

import com.iscas.biz.service.common.SystemLogService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/25 14:30
 * @since jdk1.8
 */
@RestController
@RequestMapping("/system/log")
public class SystemLogController extends BaseController {
    private final SystemLogService systemLogService;

    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping("/tree")
    public TreeResponse getTree() throws BaseException {
        return systemLogService.getTree();
    }

}
