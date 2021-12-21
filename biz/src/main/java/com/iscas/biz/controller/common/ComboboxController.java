package com.iscas.biz.controller.common;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.service.common.OprationService;
import com.iscas.biz.service.common.OrgService;
import com.iscas.biz.service.common.RoleService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@ConditionalOnMybatis
public class ComboboxController extends BaseController {
    private final RoleService roleService;
    private final OrgService orgService;
    private final OprationService oprationService;

    public ComboboxController(RoleService roleService, OrgService orgService, OprationService oprationService) {
        this.roleService = roleService;
        this.orgService = orgService;
        this.oprationService = oprationService;
    }

    @ApiOperation(value="获取角色下拉列表", notes="获取角色下拉列表")
    @GetMapping("/role")
    public ResponseEntity roleCombobox() {
        return getResponse().setValue(roleService.combobox());
    }

    @ApiOperation(value="获取组织机构树-2021-02-22 create by 朱全文", notes="")
    @GetMapping("/org/tree")
    public ResponseEntity orgTree() {
        return getResponse().setValue(orgService.getTree());
    }

    @ApiOperation(value="获取操作下拉列表-2021-02-22 create by 朱全文", notes="")
    @GetMapping("/opration")
    public ResponseEntity opration() {
        return getResponse().setValue(oprationService.combobox());
    }

}
