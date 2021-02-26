package com.iscas.biz.controller.common;

import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.exception.ValidDataException;
import com.iscas.templet.view.table.TableSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * websocket消息控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/26
 * @since jdk1.8
 */
@Api(tags = "消息管理")
@RestController
@RequestMapping("/wsData")
public class WsDataController extends BaseController {
    private String tableIdentity = "ws_data";
    private final TableDefinitionService tableDefinitionService;

    public WsDataController(TableDefinitionService tableDefinitionService) {
        this.tableDefinitionService = tableDefinitionService;
    }

    @ApiOperation(value="获取表头", notes="不带数据，带下拉列表")
    @GetMapping(value = "/header", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getTableHeaderWithOption() throws BaseException {
        return tableDefinitionService.getHeaderWithOption(tableIdentity);
    }

    @ApiOperation(value="查询数据", notes="查询角色数据，提交TableSearchRequest")
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
                    @ApiImplicitParam(name = "data", value = "新增的数据", required = true, dataType = "Map")
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
//
//        forceItem.put("role_update_time", DateSafeUtils.format(new Date(), DateSafeUtils.PATTERN));
        return tableDefinitionService.saveData(tableIdentity, data, false, null, null);
    }
}
