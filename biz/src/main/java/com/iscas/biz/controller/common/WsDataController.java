package com.iscas.biz.controller.common;

import com.iscas.biz.mp.table.service.TableDefinitionService;
import com.iscas.biz.service.common.WsService;
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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    private final WsService wsService;

    public WsDataController(TableDefinitionService tableDefinitionService, WsService wsService) {
        this.tableDefinitionService = tableDefinitionService;
        this.wsService = wsService;
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

    @ApiOperation(value="重新发送", notes="重新发送")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
            }
    )
    @PutMapping("/send/{id}")
    public ResponseEntity getData(@PathVariable Integer id) {
        wsService.retry(id);
        return getResponse();
    }

    /**
     * 接收消息回执
     * */
    @ApiOperation(value="消息回执-websocket消息", notes="")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "msgId", value = "消息ID", required = true, dataType = "String")
            }
    )
    @MessageMapping(value = "/ack/{msgId}")
    public void pathTest(Principal principal, @DestinationVariable String msgId) {
        wsService.ack(msgId);
    }
}
