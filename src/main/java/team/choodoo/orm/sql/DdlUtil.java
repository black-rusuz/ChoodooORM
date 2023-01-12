package team.choodoo.orm.sql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static team.choodoo.orm.sql.CommonSql.*;
import static team.choodoo.orm.sql.Converters.getMap;

public class DdlUtil {

    enum Type {
        LONG,
        INTEGER,
        DOUBLE,
        BOOLEAN,
        STRING,
        UNDEFINED;

        public static <T> Type getInstance(Class<T> type) {
            try {
                return Type.valueOf(type.getSimpleName().toUpperCase());
            } catch (Exception e) {
                return Type.UNDEFINED;
            }
        }
    }

    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private static final String COLUMN_TYPE_LONG = " LONG";
    private static final String COLUMN_TYPE_INTEGER = " INTEGER";
    private static final String COLUMN_TYPE_DOUBLE = " DOUBLE";
    private static final String COLUMN_TYPE_BOOLEAN = " BIT";
    private static final String COLUMN_TYPE_STRING = " VARCHAR";

    public static <T> String createTable(T bean) {
        String tableName = getTableName(bean.getClass());
        String columns = mapToColumns(getMap(bean));
        return String.format(SQL_CREATE_TABLE_IF_NOT_EXISTS, tableName, columns);
    }

    private static String mapToColumns(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(DdlUtil::mapper)
                .collect(Collectors.joining(SQL_COMMA));
    }

    private static String mapper(Map.Entry<String, Object> entry) {
        String key = entry.getKey();
        if (key.equalsIgnoreCase(ID))
            return getColumnName(key) + COLUMN_TYPE_LONG + COLUMN_PRIMARY_KEY;

        Type type = Type.getInstance(entry.getValue().getClass());
        String columnName = getColumnName(key);
        return switch (type) {
            case LONG -> columnName + COLUMN_TYPE_LONG;
            case INTEGER -> columnName + COLUMN_TYPE_INTEGER;
            case DOUBLE -> columnName + COLUMN_TYPE_DOUBLE;
            case BOOLEAN -> columnName + COLUMN_TYPE_BOOLEAN;
            case STRING, UNDEFINED -> columnName + COLUMN_TYPE_STRING;
        };
    }
}
