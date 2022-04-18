package com.iscas.flowable.modules.flowable.core.modelinfo;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:20
 * @since jdk11
 */
public interface InfoMapper {
    /**
     * map
     *
     * @param element element
     * @return com.fasterxml.jackson.databind.node.ArrayNode
     * @date 2022/4/18
     * @since jdk11
     */
    ArrayNode map(Object element);
}
