package team.choodoo.orm.sql;

import java.util.LinkedHashMap;

import static team.choodoo.orm.sql.CommonSql.getIdColumnName;
import static team.choodoo.orm.sql.CommonSql.getTableName;
import static team.choodoo.orm.sql.Converters.*;

public class DmlUtil {
    private static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    private static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE " + getIdColumnName() + " = %d;";
    private static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES (%s);";
    private static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE " + getIdColumnName() + " = %d;";
    private static final String UPDATE_TABLE_SET_BY_ID = "UPDATE %s SET %s WHERE " + getIdColumnName() + " = %d;";

    public static <T> String selectAllFromTable(Class<T> type) {
        String tableName = getTableName(type);
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    public static <T> String selectFromTableById(Class<T> type, long id) {
        String tableName = getTableName(type);
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String insertIntoTableValues(T bean) {
        String tableName = getTableName(bean.getClass());
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, mapToValues(map));
    }

    public static <T> String deleteFromTableById(T bean, long id) {
        String tableName = getTableName(bean.getClass());
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String updateTableSetById(T bean, long id) {
        String tableName = getTableName(bean.getClass());
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(UPDATE_TABLE_SET_BY_ID, tableName, mapToKeyValues(map), id);
    }
}
