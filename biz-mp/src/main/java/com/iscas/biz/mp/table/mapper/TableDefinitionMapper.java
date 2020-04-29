package com.iscas.biz.mp.table.mapper;


import com.iscas.biz.mp.table.model.ColumnDefinition;
import com.iscas.biz.mp.table.model.TableDefinition;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ZQM on 2016/5/27.
 */
@Mapper
@Repository
public interface TableDefinitionMapper {
	@Select("SELECT * FROM ${tableDefinitionTableName} WHERE `tableIdentity` = #{tableIdentity} LIMIT 0,1 ")
	TableDefinition getTableByIdentify(@Param("tableDefinitionTableName") String tableDefinitionTableName, @Param("tableIdentity") String tableIdentity);

	@Select("SELECT * FROM ${columnDefinitionTableName} WHERE `tableIdentity` = #{tableIdentity} ORDER BY sequence")
	List<ColumnDefinition> getHeaderByIdentify(@Param("columnDefinitionTableName") String columnDefinitionTableName, @Param("tableIdentity") String tableIdentity);

	@Select("SELECT ${id} as id,${value} as value FROM ${tableName} ORDER BY id")
	List<Map<Object,Object>> getRefTable(@Param("tableName") String tableName, @Param("id") String id, @Param("value") String value);

	@Select("${sql}")
	List<Map<String,Object>> getDataBySql(@Param("sql") String sql, @Param("param") Map<String, Object> param);

	@Select("${sql}")
	int getCountBySql(@Param("sql") String sql, @Param("param") Map<String, Object> param);

	@Select("SELECT COLUMN_NAME from INFORMATION_SCHEMA.columns where TABLE_NAME = #{tableName} AND TABLE_SCHEMA = (select database())")
	List<String> getTableColumns(@Param("tableName") String tableName);


	@Insert("${sql}")//需要用replace
	@Options( useGeneratedKeys=true, keyProperty= "param.id")
//	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="param.id",keyColumn="id", before=false, resultType=Integer.class)
	int saveData(@Param("sql") String sql, @Param("param") Map<String, Object> param);

	@Delete("delete from ${tableName} where  ${primaryKey} = #{value}")//需要用replace
	int deleteData(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("value") Object value);


	@Select("SELECT COUNT(${field}) AS COUNT from (${selectSql}) t where t.${field} = #{fieldValue}")
	int getCountByField(@Param("selectSql") String selectSql, @Param("field") String field, @Param("fieldValue") Object fieldValue);
}
