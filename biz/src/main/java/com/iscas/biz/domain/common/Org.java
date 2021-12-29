package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel("组织结构")
@TableName(value = "org", excludeProperty = {"roleNames", "roleIds"})
public class Org {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer orgId;

    @ApiModelProperty("名称")
    @NotEmpty(message = "{org.name.empty.constraint.message}")
    @Size(min = 2, max = 50, message = "{org.name.size.constraint.message}")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String orgName;

    @ApiModelProperty("父ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer orgPid;

    @ApiModelProperty("创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date orgCreateTime;

    @ApiModelProperty("修改时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date orgUpdateTime;

    @ApiModelProperty("描述")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String orgDesc;

    @ApiModelProperty("角色(显示)")
    private String roleNames;

    @ApiModelProperty("角色ID(不显示)")
    private List<Integer> roleIds = new ArrayList<>();

}