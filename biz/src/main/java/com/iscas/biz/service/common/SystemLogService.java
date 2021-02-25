package com.iscas.biz.service.common;

import com.iscas.biz.model.common.LogTreeDataDTO;
import com.iscas.templet.exception.BaseException;
import com.iscas.templet.view.tree.TreeResponse;
import com.iscas.templet.view.tree.TreeResponseData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 系统日志控制器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/25 14:32
 * @since jdk1.8
 */
@Service
public class SystemLogService {

    public TreeResponse getTree() throws BaseException {
        File file = new File("logs");
        if (!file.exists()) {
            file = new File(new File(System.getProperty("user.dir")), "logs");
        }
        if (!file.exists()) {
            throw new BaseException("日志目录不存在");
        }
        TreeResponse treeResponse = new TreeResponse();
        TreeResponseData<LogTreeDataDTO> root = new TreeResponseData<>();
        root.setValue(file.getAbsolutePath());
        root.setId(file.getAbsolutePath());
        root.setLabel("系统日志");
        LogTreeDataDTO logTreeDataDTO = new LogTreeDataDTO();
        logTreeDataDTO.setFilePath(file.getAbsolutePath());
        logTreeDataDTO.setFile(false);
        logTreeDataDTO.setFileName(file.getName());
        cascadeGetTree(root, file);
        treeResponse.setValue(root);
        return treeResponse;
    }
    private void cascadeGetTree(TreeResponseData<LogTreeDataDTO> treeResponseData, File file) {
        File[] files = file.listFiles();
        if (ArrayUtils.isNotEmpty(files)) {
            for (File file1 : files) {
                String name = file1.getName();
                String absolutePath = file1.getAbsolutePath();
                boolean isDir = file1.isDirectory();
                LogTreeDataDTO logTreeDataDTO = new LogTreeDataDTO();
                logTreeDataDTO.setFilePath(absolutePath)
                        .setFile(!isDir)
                        .setFileName(name);
                TreeResponseData<LogTreeDataDTO> child = new TreeResponseData<>();
                child.setLabel(name)
                        .setId(absolutePath)
                        .setValue(absolutePath)
                        .setData(logTreeDataDTO);
                treeResponseData.getChildren().add(child);
                if (isDir) {
                    cascadeGetTree(child, file1);
                }
            }
        }
    }
}
