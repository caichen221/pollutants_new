package com.iscas.biz.flowable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.domain.common.Role;
import com.iscas.biz.domain.common.User;
import com.iscas.biz.flowable.domain.dto.FlowProcDefDto;
import com.iscas.biz.flowable.domain.vo.FlowSaveXmlVo;
import com.iscas.biz.flowable.service.IFlowDefinitionService;
import com.iscas.biz.service.common.RoleService;
import com.iscas.biz.service.common.UserService;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseRuntimeException;
import com.iscas.templet.view.table.TableResponse;
import com.iscas.templet.view.table.TableResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 工作流程定义
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/20 14:19
 * @since jdk11
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
@Slf4j
@Api(tags = "流程定义")
@RestController
@RequestMapping("/flowable/definition")
@RequiredArgsConstructor
public class FlowDefinitionController {

    private final IFlowDefinitionService flowDefinitionService;

    private final UserService userService;

    private final RoleService sysRoleService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取流程定义的列表")
    public TableResponse list(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                              @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize,
                              @ApiParam(value = "流程名称") @RequestParam(required = false) String name) {
        Page<FlowProcDefDto> page = flowDefinitionService.list(name, pageNum, pageSize);
        TableResponseData<List<FlowProcDefDto>> tableResponseData = new TableResponseData<>();
        tableResponseData.setRows(page.getTotal());
        tableResponseData.setData(page.getRecords());
        TableResponse tableResponse = new TableResponse();
        tableResponse.setValue(tableResponseData);
        return tableResponse;
    }


    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
    @PostMapping("/import")
    public ResponseEntity importFile(@ApiParam(value = "流程名称", required = true) @RequestParam(required = false) String name,
                                     @ApiParam(value = "流程分类", required = true) @RequestParam(required = false) String category,
                                     MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            flowDefinitionService.importFile(name, category, in);
            return new ResponseEntity("导入成功");
        } catch (Exception e) {
            throw new BaseRuntimeException("导入失败", e);
        }
    }

    @ApiOperation(value = "读取xml文件")
    @GetMapping("/readXml/{deployId}")
    public ResponseEntity readXml(@ApiParam(value = "deployId") @PathVariable(value = "deployId") String deployId) {
        try {
            return flowDefinitionService.readXml(deployId);
        } catch (Exception e) {
            throw new BaseRuntimeException("加载xml文件异常", e);
        }

    }

    @ApiOperation(value = "读取图片文件")
    @GetMapping("/readImage/{deployId}")
    public void readImage(@ApiParam(value = "deployId") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
        OutputStream os;
        BufferedImage image;
        try {
            image = ImageIO.read(flowDefinitionService.readImage(deployId));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
            os.flush();
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
    }


    @ApiOperation(value = "保存流程设计器内的xml文件")
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody FlowSaveXmlVo vo) {
        try (InputStream in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8))) {
            flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
            return new ResponseEntity("导入成功");
        } catch (Exception e) {
            throw new BaseRuntimeException("导入失败", e);
        }
    }


    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/start/{procDefId}")
    public ResponseEntity start(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                                @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        // 在用户发起流程时调用此接口
        return flowDefinitionService.startProcessInstanceById(procDefId, variables);
    }

    @ApiOperation(value = "激活或挂起流程定义")
    @PutMapping(value = "/updateState")
    public ResponseEntity updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                      @ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return new ResponseEntity();
    }

    @ApiOperation(value = "删除流程")
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return new ResponseEntity<>();
    }

    @ApiOperation(value = "指定流程办理人员列表")
    @GetMapping("/userList")
    public ResponseEntity userList() {
        List<User> list = userService.list();
        return new ResponseEntity().setValue(list);
    }

    @ApiOperation(value = "指定流程办理组列表")
    @GetMapping("/roleList")
    public ResponseEntity roleList() {
        List<Role> list = sysRoleService.list();
        return new ResponseEntity().setValue(list);
    }

}
