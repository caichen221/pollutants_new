package com.iscas.biz.controller.common.auth;

import com.iscas.biz.domain.common.Menu;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.service.common.MenuService;
import com.iscas.common.tools.assertion.AssertCollectionUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/2/22 8:22
 * @since jdk1.8
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
@ConditionalOnMybatis
public class MenuController extends BaseController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value="[菜单管理]获取菜单树-2021-02-22", notes="create by:朱全文")
    @GetMapping
    public TreeResponse get() throws BaseException {
        return getTreeResponse().setValue(menuService.getTree());
    }

    @ApiOperation(value="[菜单管理]新增组织机构节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menu", value = "菜单数据", required = true, dataType = "Menu")
            }
    )
    @PostMapping("/node")
    public ResponseEntity addNode(@Valid @RequestBody Menu menu) throws BaseException {
        return getResponse().setValue(menuService.addMenu(menu));
    }

    @ApiOperation(value="[菜单管理]修改菜单节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menu", value = "菜单数据", required = true, dataType = "Menu")
            }
    )
    @PutMapping("/node")
    public ResponseEntity editNode(@Valid @RequestBody Menu menu) throws BaseException {
        return getResponse().setValue(menuService.editMenu(menu));
    }

    @ApiOperation(value="[菜单管理]删除菜单节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menuIds", value = "菜单Ids", required = true, dataType = "List")
            }
    )
    @PostMapping("/node/del")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
    public ResponseEntity deleteNode(@RequestBody List<Integer> menuIds) throws BaseException {
        AssertCollectionUtils.assertCollectionNotEmpty(menuIds, "menuIds不能未空");
        menuIds.forEach(menuService::deleteMenu);
        return getResponse();
    }
}
