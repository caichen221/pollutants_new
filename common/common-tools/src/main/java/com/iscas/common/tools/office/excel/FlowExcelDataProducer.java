package com.iscas.common.tools.office.excel;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/2 19:46
 * @since jdk1.8
 */
@FunctionalInterface
public interface FlowExcelDataProducer {
    /**
     * times 当前调用的次数
     * sheetName 单元格的名字
     * */
    ExcelUtils.ExcelResult supply(int times, String sheetName);
}
