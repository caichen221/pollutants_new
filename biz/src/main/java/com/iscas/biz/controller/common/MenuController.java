package com.iscas.biz.controller.common;

import com.iscas.biz.domain.common.Menu;
import com.iscas.biz.service.common.MenuService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import com.iscas.templet.view.tree.TreeResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 菜单管理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/22 8:22
 * @since jdk1.8
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value="[菜单管理]获取菜单树-2021-02-22", notes="create by:朱全文")
    @GetMapping
    public TreeResponse get() throws BaseException {
        TreeResponse treeResponse = new TreeResponse();
        treeResponse.setMessage("操作成功");
        TreeResponseData<Menu> treeResponseData = menuService.getTree();
        treeResponse.setValue(treeResponseData);
        return treeResponse;
    }

    @ApiOperation(value="[菜单管理]新增组织机构节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menu", value = "菜单数据", required = true, dataType = "Menu")
            }
    )
    @PostMapping("/node")
    public ResponseEntity addNode(@Valid @RequestBody Menu menu) throws BaseException {
        ResponseEntity response = getResponse();
        int result = menuService.addMenu(menu);
        response.setValue(result);
        return response;
    }

    @ApiOperation(value="[菜单管理]修改菜单节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menu", value = "菜单数据", required = true, dataType = "Menu")
            }
    )
    @PutMapping("/node")
    public ResponseEntity editNode(@Valid @RequestBody Menu menu) throws BaseException {
        ResponseEntity response = getResponse();
        int result = menuService.editMenu(menu);
        response.setValue(result);
        return response;
    }

    @ApiOperation(value="[菜单管理]删除菜单节点-2021-02-22", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "menuId", value = "菜单Id", required = true, dataType = "Integer")
            }
    )
    @DeleteMapping("/node/{menuId:[0-9]+}")
    public ResponseEntity deleteNode(@PathVariable Integer menuId) throws BaseException {
        ResponseEntity response = getResponse();
        int result = menuService.deleteMenu(menuId);
        response.setValue(result);
        return response;
    }
}
