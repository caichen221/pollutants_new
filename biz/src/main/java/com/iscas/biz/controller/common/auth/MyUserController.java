package com.iscas.biz.controller.common.auth;

import com.iscas.base.biz.aop.auth.SkipAuthentication;
import com.iscas.base.biz.model.auth.AuthContext;
import com.iscas.base.biz.util.AuthContextHolder;
import com.iscas.biz.domain.common.User;
import com.iscas.biz.mapper.common.UserMapper;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.common.UserService;
import com.iscas.biz.validator.anno.UserConstraint;
import com.iscas.biz.validator.anno.UserPwdConstraint;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.TableSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/2/22 11:15
 * @since jdk1.8
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Validated
@ConditionalOnMybatis
public class MyUserController extends BaseController {

    private String tableIdentity = "user";
    private final TableDefinitionService tableDefinitionService;
    private final UserService userService;
    private final UserMapper userMapper;

    public MyUserController(TableDefinitionService tableDefinitionService, UserService userService, UserMapper userMapper) {
        this.tableDefinitionService = tableDefinitionService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @SkipAuthentication
    @ApiOperation(value = "获取表头", notes = "不带数据，带下拉列表")
    @GetMapping(value = "/header", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getTableHeaderWithOption() throws BaseException {
        return tableDefinitionService.getHeaderWithOption(tableIdentity);
    }

    @ApiOperation(value = "查询用户数据", notes = "查询用户数据，提交TableSearchRequest")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "request", value = "查询条件", required = true, dataType = "TableSearchRequest"),
                    @ApiImplicitParam(name = "orgId", value = "组织机构ID, 带组织机构查询的时候传入", required = false, dataType = "Integer")
            }
    )
    @PostMapping
    public ResponseEntity getData(@RequestBody TableSearchRequest request, @RequestParam(required = false) Integer orgId)
            throws ValidDataException {
        return userService.search(request, orgId);
    }

    @ApiOperation(value = "删除用户数据", notes = "根据主键删除数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "ids", value = "id的集合", required = true, dataType = "List")
            }
    )
    @PostMapping("/del")
    public ResponseEntity deleteData(@RequestBody List<Object> ids)
            throws ValidDataException {
        userService.deleteCache(ids);
        return tableDefinitionService.batchDeleteData(tableIdentity, ids);
    }

    @ApiOperation(value = "新增用户数据", notes = "插入")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "新增的数据", required = true, dataType = "Map")
            }
    )
    @PostMapping("/data")
    public ResponseEntity saveData(@RequestBody @UserConstraint Map<String, Object> data)
            throws ValidDataException, NoSuchAlgorithmException {
        userService.deleteOneUserCache((String) data.get("user_name"));
        return userService.add(data);
    }

    @ApiOperation(value = "修改用户数据-2021-02-22", notes = "更新-create by 朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "修改的数据(未变动的数据也传)", required = true, dataType = "Map")
            }
    )
    @PutMapping("/data")
    public ResponseEntity editData(@RequestBody @UserConstraint Map<String, Object> data) throws ValidDataException {
        //先删除缓存
        Integer userId = (Integer) data.get("user_id");
        if (userId != null) {
            User user = userMapper.selectById(userId);
            if (user != null) userService.deleteOneUserCache(user.getUserName());
        }
        return userService.edit(data);
    }

    @ApiOperation(value = "修改密码-2021-02-23", notes = "更新-create by 朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "data", value = "修改的数据(未变动的数据也传)", required = true, dataType = "Map")
            }
    )
    @PutMapping("/pwd/{userId:[0-9]+}")
    public ResponseEntity changePwd(@PathVariable Integer userId, @RequestBody @UserPwdConstraint Map<String, Object> data)
            throws BaseException, NoSuchAlgorithmException {
        userService.changePwd(userId, data);
        return getResponse();
    }
}
