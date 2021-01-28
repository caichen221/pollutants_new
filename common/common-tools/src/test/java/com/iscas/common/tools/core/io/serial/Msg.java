package com.iscas.common.tools.core.io.serial;



public class Msg {
    private byte[] version_flag;
    private short crc_code;
    private byte[] msg_body;

    public byte[] getVersion_flag() {
        return version_flag;
    }

    public void setVersion_flag(byte[] version_flag) {
        this.version_flag = version_flag;
    }

    public short getCrc_code() {
        return crc_code;
    }

    public void setCrc_code(short crc_code) {
        this.crc_code = crc_code;
    }

    public byte[] getMsg_body() {
        return msg_body;
    }

    public void setMsg_body(byte[] msg_body) {
        this.msg_body = msg_body;
    }
}