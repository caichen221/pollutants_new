package com.iscas.common.k8s.tools.model.volume;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 14:48
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcVoNfsParam extends KcVolumeParam{

    /**
     * nfs-server地址
     * */
    private String nfsServerAddress;

    /**
     * nfs path
     * */
    private String nfsPath;

}
