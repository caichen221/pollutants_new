package com.iscas.common.k8s.tools.model.volume;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2019/12/9 14:51
 * @since jdk1.8
 */
@SuppressWarnings("unused")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KcVoEmptyDirParam extends KcVolumeParam {

}
