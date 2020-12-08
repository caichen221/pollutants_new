package com.iscas.common.harbor.tools.model;

import lombok.Data;

/**
 * 镜像信息
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/8 21:20
 * @since jdk1.8
 */
@Data
public class Repositroy {
    /**project ID*/
    private String projectId;

    /**project name*/
    private String projectName;

    /**project是否公开*/
    private boolean projectPublic;

    /**下载的次数*/
    private Integer pullCount;

    /**
     * 镜像的名字
     * */
    private String imageName;

    /**
     * Tag数量
     * */
    private Integer tagsCount;




}
