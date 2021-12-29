package com.iscas.biz.domain.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "menu_opration")
@Data
public class MenuOprationKey {

    private Integer menuId;

    private Integer opId;

}