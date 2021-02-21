package com.iscas.biz.controller.common;

import com.iscas.biz.service.common.RoleService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.ComboboxData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 下拉列表
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/21 19:48
 * @since jdk1.8
 */
@RestController
@RequestMapping("/combobox")
@Api(tags = "下拉列表")
public class ComboboxController extends BaseController {
    private final RoleService roleService;

    public ComboboxController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value="获取角色下拉列表", notes="获取角色下拉列表")
    @GetMapping("/role")
    public ResponseEntity saveData() {
        ResponseEntity response = getResponse();
        List<ComboboxData> combobox = roleService.combobox();
        response.setValue(combobox);
        return response;
    }
}
