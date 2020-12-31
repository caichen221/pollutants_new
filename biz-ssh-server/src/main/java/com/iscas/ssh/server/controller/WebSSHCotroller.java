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

}
