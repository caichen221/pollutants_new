package sql;

import java.sql.SQLException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/1 14:36
 * @since jdk1.8
 */
public class SqlErrorUtils {

    public enum DatabaseType {
        MySQL, H2, SQLServer, DB2, PostgreSQL, Oracle;
    }

    private boolean isDuplicateRecord(final SQLException ex, DatabaseType databaseType) {
        return DatabaseType.MySQL.equals(databaseType) && 1062 == ex.getErrorCode() || DatabaseType.H2.equals(databaseType) && 23505 == ex.getErrorCode()
                || DatabaseType.SQLServer.equals(databaseType) && 1 == ex.getErrorCode() || DatabaseType.DB2.equals(databaseType) && -803 == ex.getErrorCode()
                || DatabaseType.PostgreSQL.equals(databaseType) && 0 == ex.getErrorCode() || DatabaseType.Oracle.equals(databaseType) && 1 == ex.getErrorCode();
    }
}
