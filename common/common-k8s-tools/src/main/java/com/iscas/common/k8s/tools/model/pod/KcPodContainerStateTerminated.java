package com.iscas.common.k8s.tools.model.pod;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/31 18:23
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcPodContainerStateTerminated {
    private Integer exitCode;
    private Date finishedAt;
    private String message;
    private String reason;
    private Integer signal;
    private Date startedAt;

}
