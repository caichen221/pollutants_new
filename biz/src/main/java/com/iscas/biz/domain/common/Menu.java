package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ApiModel("菜单")
@Data
@Accessors(chain = true)
@TableName(value = "menu", excludeProperty = {"roleIds", "roleNames", "oprationIds", "oprationNames"})
public class Menu implements Serializable {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty("上级菜单")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer menuPid;

    @ApiModelProperty("菜单路径")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String menuPage;

    @ApiModelProperty("菜单创建时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date menuCreateTime;

    @ApiModelProperty("菜单修改时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date menuUpdateTime;

    @ApiModelProperty("菜单名称")
    @NotEmpty(message = "{menu.name.empty.constraint.message}")
    @Size(min = 2, max = 50, message = "{menu.name.size.constraint.message}")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
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