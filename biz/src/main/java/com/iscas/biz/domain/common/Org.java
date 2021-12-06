package com.iscas.biz.domain.common;

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
public class Org {

    @ApiModelProperty("ID")
    private Integer orgId;

    @ApiModelProperty("名称")
    @NotEmpty(message = "{org.name.empty.constraint.message}")
    @Size(min = 2, max = 50, message = "{org.name.size.constraint.message}")
    private String orgName;

    @ApiModelProperty("父ID")
    private Integer orgPid;

    @ApiModelProperty("创建时间")
    private Date orgCreateTime;

    @ApiModelProperty("修改时间")
    private Date orgUpdateTime;

    @ApiModelProperty("描述")
    private String orgDesc;

    @ApiModelProperty("角色(显示)")
    private String roleNames;

    @ApiModelProperty("角色ID(不显示)")
    private List<Integer> roleIds = new ArrayList<>();

}