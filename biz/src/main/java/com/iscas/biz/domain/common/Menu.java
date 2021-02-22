package com.iscas.biz.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ApiModel("菜单")
@Data
@Accessors(chain = true)
public class Menu {
    @ApiModelProperty("ID")
    private Integer menuId;

    @ApiModelProperty("上级菜单")
    private Integer menuPid;

    @ApiModelProperty("菜单路径")
    private String menuPage;

    @ApiModelProperty("菜单创建时间")
    private Date menuCreateTime;

    @ApiModelProperty("菜单修改时间")
    private Date menuUpdateTime;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("角色ID（多选）不显示")
    private List<Integer> roleIds = new ArrayList<>();

    @ApiModelProperty("角色名称，显示")
    private String roleNames;

    @ApiModelProperty("权限标识ID,不显示")
    private List<Integer> oprationIds = new ArrayList<>();

    @ApiModelProperty("权限标识名称，显示")
    private String oprationNames;

}