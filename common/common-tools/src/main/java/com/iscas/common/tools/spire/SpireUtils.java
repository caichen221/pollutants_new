package com.iscas.common.tools.spire;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.TableRow;
import com.spire.doc.interfaces.ITable;

/**
 * Spire.doc 工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/11/30 15:17
 * @since jdk1.8
 */
public class SpireUtils {
    private SpireUtils() {}

    /**
     * 复制行
     *
     * @param sectionIndex section索引
     * @param tableIndex   表的索引
     * @param srcFilePath  原始模板路径
     * @param dstFilePath  目标路径
     * @param rowOffset    要扩容的行下标
     * @param copyCount    需要复制的行数
     * @since jdk1.8
     */
    public static void copyRow(int sectionIndex, int tableIndex, String srcFilePath, String dstFilePath, int rowOffset, int copyCount) {
        Document doc = new Document();
        doc.loadFromFile(srcFilePath);
        //获取第n节
        Section section = doc.getSections().get(sectionIndex);
        //获取第n个表格
        ITable table = section.getTables().get(tableIndex);
        TableRow tableRow = table.getRows().get(rowOffset);
        for (int i = 0; i < copyCount; i++) {
            TableRow cloneTableRow = tableRow.deepClone();
            table.getRows().insert(rowOffset + (i + 1), cloneTableRow);
        }
        doc.saveToFile(dstFilePath, FileFormat.Docx_2013);
        doc.dispose();
    }
}
