package com.iscas.templet.view.table;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhuquanwen
 * @date 2017/12/25 16:54
 **/
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseData<List> implements Serializable {
    /**
     * 返回总条目
     */
    protected Long rows;
    /**
     * 返回的具体数据，是个集合
     */
    private List data;

}
