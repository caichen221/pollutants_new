package com.iscas.biz.mp.table.controller;



import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.TableSearchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登陆控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 22:38
 * @since jdk1.8
 */
@RestController
@RequestMapping("/table")
//@Api(description = "表格控制器")
@ConditionalOnMybatis
public class TableDefinitionController extends BaseController {
    @Autowired
	private TableDefinitionService tableDefinitionService;

//	@ApiOperation(value="获取表头", notes="不带数据，不带下拉列表")
    @GetMapping(value = "/{tableIdentity}/header", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity getTableHeader(@PathVariable String tableIdentity) throws BaseException {
		return tableDefinitionService.getTableHeader(tableIdentity);
    }

//	@ApiOperation(value="获取表头", notes="不带数据，带下拉列表")
	@GetMapping(value = "/{tableIdentity}/headerWithOption", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity getTableHeaderWithOption(@PathVariable String tableIdentity) throws BaseException {
		return tableDefinitionService.getHeaderWithOption(tableIdentity);
	}

//	@ApiOperation(value="查询表格数据", notes="不带表头")
	@PostMapping(value = "/{tableIdentity}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity getData(@PathVariable String tableIdentity, @RequestBody TableSearchRequest request)
		throws ValidDataException {
		return tableDefinitionService.getData(tableIdentity, request, null);
	}

//	@ApiOperation(value="删除数据", notes="根据主键删除数据")
	@DeleteMapping( value = "/{tableIdentity}/{primaryKey}")
	public ResponseEntity deleteData(@PathVariable String tableIdentity, @PathVariable Object primaryKey)
		throws ValidDataException {
		return tableDefinitionService.deleteData(tableIdentity, primaryKey);
	}

//	@ApiOperation(value="保存数据", notes="插入或更新")
	@PutMapping(value = "/{tableIdentity}")
	public ResponseEntity saveData(@PathVariable String tableIdentity, @RequestBody Map<String,Object> data)
		throws ValidDataException {
		return tableDefinitionService.saveData(tableIdentity, data, false);
	}
}
