package com.iscas.common.k8s.tools.model.volume;

/**
 * 数据卷
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 14:45
 * @since jdk1.8
 */
public class KcVolume {
    /**
     * 名称
     * */
    private String name;

    /**
     * 类型 nfs / pv / emptyDir / hostPath / configMap / secret
     * */
    private String type;

    /**
     * 参数
     * */
    private KcVolumeParam params;


}
