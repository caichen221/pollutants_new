package com.iscas.biz.controller.common;

import com.google.common.collect.ImmutableMap;
import com.iscas.base.biz.util.JWTUtils;
import com.iscas.biz.config.log.LogRecord;
import com.iscas.biz.config.log.LogType;
import com.iscas.biz.config.log.OperateType;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.common.ParamService;
import com.iscas.common.tools.core.date.DateSafeUtils;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.AuthenticationRuntimeException;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.TableSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/26 14:37
 * @since jdk1.8
 */
@RestController
@RequestMapping("/param")
@Api(tags = "参数管理")
@ConditionalOnMybatis
public class ParamController extends BaseController {


    private final static String tableIdentity = "param";
    @Autowired
    private TableDefinitionService tableDefinitionService;
    @Autowired
    private ParamService paramService;

    @ApiOperation(value = "获取表头", notes = "不带数据，带下拉列表")
    @GetMapping(value = "/getHeader", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getTableHeaderWithOption() throws BaseException {
        return tableDefinitionService.getHeaderWithOption(tableIdentity);
    }

    @ApiOperation(value = "查询表格数据", notes = "不带表头")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "request", value = "查询条件", required = true, dataType = "TableSearchRequest")
            }
    )
    @PostMapping(value = "/getData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getData(@RequestBody TableSearchRequest request)
            throws ValidDataException {
        return tableDefinitionService.getData(tableIdentity, request, null);
    }

    @ApiOperation(value = "删除参数数据", notes = "根据主键删除数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "ids", value = "id的集合", required = true, dataType = "List")
            }
    )
    @PostMapping("/del")
    @LogRecord(type = LogType.SYSTEM, desc = "删除参数", operateType = OperateType.delete)
    public ResponseEntity deleteData(@RequestBody List<Object> ids) {
        ResponseEntity responseEntity = getResponse();
        boolean ret = paramService.deleteByIds(ids);
        responseEntity.setValue(ret);
        return responseEntity;
    }

    @ApiOperation(value = "新增参数数据", notes = "插入")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "新增的数据", required = true, dataType = "Map")
            }
    )
    @PostMapping("/data")
    @LogRecord(type = LogType.SYSTEM, desc = "新增参数", operateType = OperateType.add)
    public ResponseEntity saveData(@RequestBody Map<String, Object> data) throws ValidDataException {
        ImmutableMap<String, Object> forceItem = ImmutableMap.of("create_by", getUsername(), "create_time", DateSafeUtils.format(new Date()));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, forceItem);
    }

    @ApiOperation(value = "修改参数数据", notes = "更新")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "修改的数据(未变动的数据也传)", required = true, dataType = "Map")
            }
    )
    @PutMapping("/data")
    @CacheEvict(value = "param", key = "#data.get(\"param_key\")")
    @LogRecord(type = LogType.SYSTEM, desc = "修改参数", operateType = OperateType.update)
    public ResponseEntity editData(@RequestBody Map<String, Object> data)
            throws ValidDataException {
        ImmutableMap<String, Object> forceItem = ImmutableMap.of("update_by", getUsername(), "update_time", DateSafeUtils.format(new Date()));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, forceItem);
    }

    @GetMapping("/getParamValue/{key}")
    @ApiOperation(value = "测试", notes = "getParamValue")
    public ResponseEntity getParamValue(@PathVariable String key) {
        ResponseEntity response = getResponse();
        String value = paramService.getParamValue(key);
        response.setValue(value);
        return response;

    }

    private String getUsername() {
        String username;
        try {
            username = JWTUtils.getLoginUsername();
        } catch (AuthenticationRuntimeException e) {
            username = "unknown";
        }
        return username;
    }
}
