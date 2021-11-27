package com.iscas.templet.view.tree;

import com.iscas.templet.common.ResponseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: zhuquanwen
 * @Description:
 * @Date: 2017/12/25 17:19
 * @Modified:
 **/
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class TreeResponse extends ResponseEntity<TreeResponseData> implements Serializable{

    /**重写*/
    public TreeResponse setValue(TreeResponseData treeResponseData) {
        this.value = treeResponseData;
        return this;
    }

}
