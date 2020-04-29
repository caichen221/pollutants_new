package com.iscas.common.k8s.tools.model.deployment;

import com.iscas.common.k8s.tools.model.KcContainer;
import com.iscas.common.k8s.tools.model.node.KcNodeRuntimeInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * deployment
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/8 17:39
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcDeployment {

    /**
     * 名称
     * */
    private String name;

    /**
     * 当前副本数目
     * */
    private Integer currentRepSum;

    /**
     * 计划副本数目
     * */
    private Integer planRepSum;

    /**
     * 启动时间
     * */
    private String runtimeStr;

    /**
     * 基本信息
     * */
    private KcDepBaseInfo baseInfo;

    /**
     * 运行时信息
     * */
    private List<KcDepRuntimeInfo> runtimeInfos;

    /**
     * 容器信息
     * */
    private List<KcContainer> containers;

    /**
     * 初始化容器信息
     * */
    private KcContainer initContainer;

    /**
     * 重启策略 Always / OnFailure / Never
     * */
    private String restartStrategy = "Always";

    /**
     * 镜像拉取的秘钥
     * */
    private String imagePullSecret;
}
