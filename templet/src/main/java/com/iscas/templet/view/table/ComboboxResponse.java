package com.iscas.templet.view.table;

import com.iscas.templet.common.ResponseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 下拉列表返回结构
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/6/9 13:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComboboxResponse<T> extends ResponseEntity<List<ComboboxData<T>>> implements Serializable {
}
