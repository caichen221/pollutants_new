package com.iscas.base.biz.model.auth;

import com.sun.source.doctree.SerialDataTree;
import lombok.Data;

import java.io.Serializable;

/**
 * URL
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/7/16 18:41
 * @since jdk1.8
 */
@Data
public class Url implements Serializable {
    private String key;
    private String name;
}
