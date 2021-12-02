package com.iscas.biz.mp.table.service.interfaces;

/**
 * TableDefinition的SQL拼接的接口
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/2 13:52
 * @since jdk1.8
 */
public interface ITableDefinitionSqlCreatorService {
    String getTableByIdentifySql();

    String getHeaderByIdentifySql();

    String getRefTableSql();

    String getTableColumnsSql();

    String sql();

    String saveDataSql();

    String deleteDataSql();

    String batchDeleteDataSql();

    String getCountByFieldSql();

}
