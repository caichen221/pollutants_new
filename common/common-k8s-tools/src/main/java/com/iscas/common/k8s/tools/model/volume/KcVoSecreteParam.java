package com.iscas.common.k8s.tools.model.volume;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/9 14:51
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class KcVoSecreteParam extends KcVolumeParam {
    /**
     * secret
     * */
    private String secret;

    /**
     * key to path
     * */
    private LinkedHashMap<String, String> keyToPath;

}
