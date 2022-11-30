package com.iscas.common.tools.spire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/11/30 15:19
 * @since jdk1.8
 */
class SpireUtilsTest {
    /**
     * 测试复制行
     * */
    @Test
    public void copyRow() {
        SpireUtils.copyRow(0, 0, "C:\\Users\\Administrator\\Desktop\\三亚测试\\动态表单.docx",
                "C:\\Users\\Administrator\\Desktop\\三亚测试\\动态表单扩容.docx", 8, 3);
    }

}