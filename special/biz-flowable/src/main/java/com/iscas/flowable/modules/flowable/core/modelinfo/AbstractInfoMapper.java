package com.iscas.flowable.modules.flowable.core.modelinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/18 21:23
 * @since jdk11
 */
@SuppressWarnings("unused")
public abstract class AbstractInfoMapper implements InfoMapper {
    protected DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected ArrayNode propertiesNode;

    @Override
    public ArrayNode map(Object element) {
        propertiesNode = objectMapper.createArrayNode();
        mapProperties(element);
        return propertiesNode;
    }

    /**
     * mapProperties
     *
     * @param element element
     * @date 2022/4/18
     * @since jdk11
     */
    protected abstract void mapProperties(Object element);

    protected void createPropertyNode(String name, String value) {
        if (StringUtils.isNotEmpty(value)) {
            ObjectNode propertyNode = objectMapper.createObjectNode();
            propertyNode.put("name", name);
            propertyNode.put("value", value);
            propertiesNode.add(propertyNode);
        }
    }

    protected void createPropertyNode(String name, Date value) {
        if (value != null) {
            createPropertyNode(name, dateFormat.format(value));
        }
    }

    protected void createPropertyNode(String name, Boolean value) {
        if (value != null) {
            createPropertyNode(name, value.toString());
        }
    }

    protected void createPropertyNode(String name, List<String> values) {
        if (CollectionUtils.isNotEmpty(values)) {
            StringBuilder commaSeperatedString = new StringBuilder();
            for (String value : values) {
                if (commaSeperatedString.length() > 0) {
                    commaSeperatedString.append(", ");
                }
                commaSeperatedString.append(value);
            }
            createPropertyNode(name, commaSeperatedString.toString());
        }
    }
}
