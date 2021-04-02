package com.iscas.ssh.server.controller;

import com.iscas.ssh.server.model.WebSSHData;
import com.iscas.ssh.server.service.SSHService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.jcraft.jsch.JSchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * ssh连接控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/27 14:26
 * @since jdk1.8
 */
@RestController
@RequestMapping("/ssh/connect")
@Api(tags = "SSH连接控制器")
public class WebSSHCotroller extends BaseController {
    @Autowired
    private SSHService sshService;

    @MessageMapping("/connect")
    @ApiOperation(value="开启一个新的会话连接窗口-2020-12-27", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sshData", value = "连接信息", required = true, dataType = "WebSSHData")
            }
    )
    public ResponseEntity connect(Principal user, WebSSHData sshData) throws IOException, JSchException {
        ResponseEntity response = getResponse();
        String connectionId = sshData.getConnectionId();
        sshService.initConnection(connectionId, user);
        sshService.recvHandle(sshData);
        return response;
    }

    @MessageMapping("/command")
    @ApiOperation(value="开启一个新的会话连接窗口-2020-12-27", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sshData", value = "命令信息", required = true, dataType = "WebSSHData")
            }
    )
    public ResponseEntity command(Principal user, WebSSHData sshData) throws IOException, JSchException {
        ResponseEntity response = getResponse();
        sshService.recvHandle(sshData);
        return response;
    }

    @MessageMapping("/pong")
    @ApiOperation(value="心跳的响应-2021-01-12", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String")
            }
    )
    public ResponseEntity command(Principal user, String connectionId) throws IOException, JSchException {
        ResponseEntity response = getResponse();
        sshService.pong(connectionId);
        return response;
    }


//    @ApiOperation(value = "获取远程服务器的文件列表-2021-03-31", notes = "create by zqw")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "dir", value="目录，如果不传，默认使用/", required = false, dataType = "String")
//
//    })
//    public ResponseEntity getFileList() {
//
//    }

}
