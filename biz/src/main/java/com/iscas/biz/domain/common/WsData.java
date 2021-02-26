package com.iscas.biz.domain.common;

import com.iscas.common.web.tools.json.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WsData {
    private Integer id;

    private String type;

    private String msgId;

    private String userIdentify;

    private Boolean persistent;

    private String destination;

    private Boolean ack;

    private String data;

    public static WsData convert(com.iscas.biz.model.common.WsData wsData) {
        WsData wsData1 = new WsData();
        wsData1.setMsgId(wsData.getMsgId())
                .setAck(wsData.isAck())
                .setDestination(wsData.getDestination())
                .setPersistent(wsData.isPersistent())
                .setType(wsData.getType().toString())
                .setUserIdentify(wsData.getUserIdentity());
        String jsonData = null;
        if (wsData.getData() != null) {
            if (wsData.getData() instanceof String) {
                jsonData = wsData.getData().toString();
            } else {
                jsonData = JsonUtils.toJson(wsData.getData());
            }
        }
        wsData1.setData(jsonData);
        return wsData1;
    }

}