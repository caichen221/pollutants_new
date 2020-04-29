package com.iscas.biz.mp.config.db.multi;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 13:44
 * @since jdk1.8
 */
public enum DbTypeEnum {
    db1("mysql1"), db2("mysql2");
    private String value;

    DbTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
