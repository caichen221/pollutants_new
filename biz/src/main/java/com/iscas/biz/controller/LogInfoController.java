package com.iscas.biz.controller;

import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.LogInfoService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
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

import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/20 18:48
 * @since jdk1.8
 */
@RestController
@RequestMapping("/logInfo")
@Api(tags = "访问日志")
public class LogInfoController extends BaseController {

    private final static String tableIdentity = "log_info";
    @Autowired
    private TableDefinitionService tableDefinitionService;
    @Autowired
    private LogInfoService logInfoService;

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


    @ApiOperation(value = "删除数据", notes = "删除某个时间点之前的数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "period", value = "保存周期，保存多久的数据，例如，'period':'1s'（1秒）、1m（1分钟）、1h（1小时）、1d（1天） 等", required = true, dataType = "String")
            }
    )
    @PostMapping(value = "/deleteData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteData(@RequestBody Map requestParam) {

        ResponseEntity response = getResponse();
        Object period = requestParam.get("period");
        if (period == null){
            response.setMessage("参数为空，不执行任何操作");
           return response;
        }
        logInfoService.deleteDataByTime(period.toString());

        return response;

    }

}
