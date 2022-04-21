package com.iscas.common.k8s.tools.model.health;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 就绪检测
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2019/12/9 15:04
 * @since jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KcReadinessProbe extends KcProbe {

}
