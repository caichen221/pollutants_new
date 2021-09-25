package com.iscas.biz.controller.common;

import com.google.common.collect.ImmutableMap;
import com.iscas.base.biz.util.JWTUtils;
import com.iscas.biz.config.log.LogRecord;
import com.iscas.biz.config.log.LogType;
import com.iscas.biz.config.log.OperateType;
import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.common.DictDataService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 16:15
 * @since jdk1.8
 */
@RestController
@RequestMapping("/dictData")
@Api(tags = "字典管理")
public class DictDataController extends BaseController {

    private final static String tableIdentity = "dict_data";
    @Autowired
    private TableDefinitionService tableDefinitionService;
    @Autowired
    private DictDataService dictDataService;

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

    @ApiOperation(value = "删除字典数据", notes = "根据主键删除数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "ids", value = "id的集合", required = true, dataType = "List")
            }
    )
    @PostMapping("/del")
    @LogRecord(type = LogType.SYSTEM, desc = "删除字典数据", operateType = OperateType.delete)
    public ResponseEntity deleteData(@RequestBody List<Object> ids) {
        ResponseEntity responseEntity = getResponse();
        boolean ret = dictDataService.deleteByIds(ids);
        responseEntity.setValue(ret);
        return responseEntity;
    }

    @ApiOperation(value = "新增字典数据", notes = "插入")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "新增的数据", required = true, dataType = "Map")
            }
    )
    @PostMapping("/data")
    @LogRecord(type = LogType.SYSTEM, desc = "新增字典数据", operateType = OperateType.add)
    public ResponseEntity saveData(@RequestBody Map<String, Object> data) throws ValidDataException {
        ImmutableMap<String, Object> forceItem = ImmutableMap.of("create_by", getUsername(), "create_time", DateSafeUtils.format(new Date()));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, forceItem);
    }

    @ApiOperation(value = "修改字典数据", notes = "更新")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "data", value = "修改的数据(未变动的数据也传)", required = true, dataType = "Map")
            }
    )
    @PutMapping("/data")
    @LogRecord(type = LogType.SYSTEM, desc = "修改字典数据", operateType = OperateType.update)
    public ResponseEntity editData(@RequestBody Map<String, Object> data)
            throws ValidDataException {
        ImmutableMap<String, Object> forceItem = ImmutableMap.of("update_by", getUsername(), "update_time", DateSafeUtils.format(new Date()));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, forceItem);
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
