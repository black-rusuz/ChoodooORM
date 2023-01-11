package team.choodoo.orm.sql;

public class CommonSql {
    public static final String TABLE_PREFIX = "T_";
    public static final String COLUMN_PREFIX = "C_";
    public static final String ID = "id";
    public static final String SQL_COMMA = ", ";
    public static final String SQL_VALUE_WRAPPER = "'%s'";
    public static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";

    public static <T> String getTableName(Class<T> type) {
        return TABLE_PREFIX + type.getSimpleName();
    }

    public static String getColumnName(String key) {
        return COLUMN_PREFIX + key;
    }

    public static String getIdColumnName() {
        return getColumnName(ID);
    }

    public static String getFieldName(String columnName) {
        return columnName.replace(COLUMN_PREFIX, "").toLowerCase();
    }
}
