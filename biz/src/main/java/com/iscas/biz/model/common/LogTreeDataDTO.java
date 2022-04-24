package com.iscas.biz.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 日志树的属性
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/2/25 14:52
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
@ApiModel("日志树的属性")
public class LogTreeDataDTO {
    @ApiModelProperty("是否为文件，false代表是文件夹")
    private boolean isFile;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("文件名")
    private String fileName;
}
