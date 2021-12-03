package com.iscas.biz.controller.common.auth;

import com.iscas.base.biz.aop.auth.SkipAuthentication;
import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.common.RoleService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.TableSearchRequest;
import com.iscas.templet.view.tree.TreeResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/21 16:37
 * @since jdk1.8
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@org.springframework.validation.annotation.Validated
public class RoleController extends BaseController {
    private String tableIdentity = "role";
    private final TableDefinitionService tableDefinitionService;
    private final RoleService roleService;

    public RoleController(TableDefinitionService tableDefinitionService, RoleService roleService) {
        this.tableDefinitionService = tableDefinitionService;
        this.roleService = roleService;
    }

    @SkipAuthentication
    @ApiOperation(value="获取表头", notes="不带数据，带下拉列表")
    @GetMapping(value = "/header", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getTableHeaderWithOption() throws BaseException {
        return tableDefinitionService.getHeaderWithOption(tableIdentity);
    }

    @ApiOperation(value="查询角色数据", notes="查询角色数据，提交TableSearchRequest")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "request", value = "查询条件", required = true, dataType = "TableSearchRequest")
            }
    )
    @PostMapping
    public ResponseEntity getData(@RequestBody TableSearchRequest request)
            throws ValidDataException {
        return tableDefinitionService.getData(tableIdentity, request, null);
    }

    @ApiOperation(value="获取角色的菜单树", notes="获取角色的菜单树")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer")
            }
    )
    @GetMapping("/menu/tree")
    public ResponseEntity getMenuTree(@NotNull(message = "roleId不能为空") Integer roleId) {
        return getResponse().setValue(roleService.getMenuTree(roleId));
    }


    @ApiOperation(value="修改角色的菜单树", notes="修改角色的菜单树")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "treeResponseData", value = "角色菜单树", required = true, dataType = "TreeResponseData"),
                    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer")
            }
    )
    @PutMapping("/menu/tree")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity updateMenuTree(@RequestBody TreeResponseData treeResponseData, @NotNull(message = "roleId不能为空") Integer roleId) {
        roleService.updateMenuTree(treeResponseData, roleId);
        return getResponse();
    }

    @ApiOperation(value="获取角色对应的操作权限", notes="获取角色对应的操作权限")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer")
            }
    )
    @GetMapping("/opration")
    public ResponseEntity getOpration(@NotNull(message = "roleId不能为空") Integer roleId) {
        return getResponse().setValue(roleService.getOprations(roleId));
    }

    @ApiOperation(value="修改角色对应的操作权限", notes="修改角色对应的操作权限")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "oprations", value = "操作权限", required = true, dataType = "List"),
                    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer")
            }
    )
    @PutMapping("/opration")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity editOpration(@RequestBody List<Opration> oprations, @NotNull(message = "roleId不能为空") Integer roleId) {
        roleService.editOpration(oprations, roleId);
        return getResponse();
    }

    @ApiOperation(value="删除角色数据", notes="根据主键删除数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "ids", value = "id的集合", required = true, dataType = "List")
            }
    )
    @PostMapping("/del")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity deleteData(@RequestBody List<Object> ids)
            throws ValidDataException {
        return tableDefinitionService.batchDeleteData(tableIdentity, ids);
    }

    @ApiOperation(value="新增角色数据", notes="插入")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "新增的数据", required = true, paramType = "body", dataTypeClass = Map.class)
            }
    )
    @PostMapping("/data")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity saveData(@RequestBody Map<String,Object> data)
            throws ValidDataException {
        return tableDefinitionService.saveData(tableIdentity, data, false);
    }

    @ApiOperation(value="修改角色数据", notes="更新")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "修改的数据(未变动的数据也传)", required = true, dataType = "Map")
            }
    )
    @PutMapping("/data")
    @Caching(evict = {
            @CacheEvict(value = "auth", key = "'url_map'"),
            @CacheEvict(value = "auth", key = "'menus'"),
            @CacheEvict(value = "auth", key = "'role_map'")
    })
    public ResponseEntity editData(@RequestBody Map<String,Object> data)
            throws ValidDataException {
        //修改时间
//        Map<String, Object> forceItem = new HashMap<>();
//        forceItem.put("role_update_time", DateSafeUtils.format(new Date(), DateSafeUtils.PATTERN));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, null);
    }
}
