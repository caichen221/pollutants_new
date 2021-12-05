package com.iscas.ssh.server.controller;

import com.iscas.ssh.server.model.SftpFile;
import com.iscas.ssh.server.model.WebSSHData;
import com.iscas.ssh.server.service.SSHService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import com.jcraft.jsch.JSchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
        sshService.initConnection(sshData.getConnectionId(), user);
        sshService.recvHandle(sshData);
        return getResponse();
    }

    @MessageMapping("/command")
    @ApiOperation(value="开启一个新的会话连接窗口-2020-12-27", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sshData", value = "命令信息", required = true, dataType = "WebSSHData")
            }
    )
    public ResponseEntity command(Principal user, WebSSHData sshData) throws IOException, JSchException {
        sshService.recvHandle(sshData);
        return getResponse();
    }

    @MessageMapping("/pong")
    @ApiOperation(value="心跳的响应-2021-01-12", notes="create by:朱全文")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String")
            }
    )
    public ResponseEntity command(Principal user, String connectionId) throws IOException, JSchException {
        sshService.pong(connectionId);
        return getResponse();
    }


    @ApiOperation(value = "获取远程服务器的文件列表-2021-04-03", notes = "create by zqw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dir", value="目录，如果不传，默认使用/", required = false, dataType = "String")

    })
    @GetMapping("/list")
    public ResponseEntity getFileList(String connectionId, @RequestParam(required = false) String dir) throws BaseException {
        try {
            List<SftpFile> sftpFiles = sshService.listDir(connectionId, dir);
            return getResponse().setValue(sftpFiles);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("获取远程服务器的文件列表出错", e);
        }
    }

    @ApiOperation(value = "从远程服务器下载文件-2021-04-03", notes = "create by zqw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "path", value="文件目录", required = true, dataType = "String")

    })
    @GetMapping("/download")
    public void download(String connectionId, String path) throws BaseException {
        try {
            sshService.download(connectionId, path);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("文件下载出错", e);
        }
    }

    @ApiOperation(value = "向远程服务器传输数据-2021-04-03", notes = "create by zqw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "files", value="上传的文件", required = true, dataType = "MultipartFile[]"),
            @ApiImplicitParam(name = "dest", value="目标目录", required = true, dataType = "String")

    })
    @PostMapping("/upload")
    public ResponseEntity upload(String connectionId, @RequestParam("files") MultipartFile[] files, String dest) throws BaseException {
        try {
            sshService.upload(connectionId, files, dest);
            return getResponse();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("文件传输出错", e);
        }
    }

    @ApiOperation(value = "创建文件夹-2021-05-06", notes = "create by zqw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "path", value="父级目录", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dirName", value="文件夹名称", required = true, dataType = "String")

    })
    @PutMapping("/dir")
    public ResponseEntity newDir(String connectionId, String path, String dirName) throws BaseException {
        try {
            sshService.newDir(connectionId, path, dirName);
            return getResponse();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("创建文件夹出错", e);
        }
    }

    @ApiOperation(value = "删除文件或文件夹-2021-05-06", notes = "create by zqw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connectionId", value = "连接ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "path", value="文件或文件夹路径", required = true, dataType = "String")

    })
    @DeleteMapping("/path")
    public ResponseEntity deletePath(String connectionId, String path) throws BaseException {
        try {
            sshService.deletePath(connectionId, path);
            return getResponse();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("删除出错", e);
        }
    }

}
