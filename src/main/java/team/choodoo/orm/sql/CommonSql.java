package team.choodoo.orm.sql;

import com.google.common.base.CaseFormat;

public class CommonSql {
    private static final String TABLE_PREFIX = "T_";
    private static final String COLUMN_PREFIX = "C_";

    public static final String ID = "id";
    public static final String SQL_COMMA = ", ";
    public static final String SQL_VALUE_WRAPPER = "'%s'";
    public static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";

    public static <T> String getTableName(Class<T> type) {
        String snakeCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, type.getSimpleName());
        return TABLE_PREFIX + snakeCase;
    }

    public static String getColumnName(String key) {
        String snakeCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, key);
        return COLUMN_PREFIX + snakeCase;
    }

    public static String getIdColumnName() {
        return getColumnName(ID);
    }

    public static String getFieldName(String columnName) {
        String noPrefix = columnName.replace(COLUMN_PREFIX, "");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, noPrefix);
    }
}
