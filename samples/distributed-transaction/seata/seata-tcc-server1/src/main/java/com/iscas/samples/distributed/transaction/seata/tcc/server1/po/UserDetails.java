package com.iscas.samples.distributed.transaction.seata.tcc.server1.po;

import lombok.Data;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 22:21
 * @since jdk1.8
 */
@Data
public class UserDetails {
    private Integer id;
    private Integer userId;
    private String description;
}
