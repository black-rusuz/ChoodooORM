package team.choodoo.orm.sql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static team.choodoo.orm.sql.CommonSql.*;
import static team.choodoo.orm.sql.Converters.getMap;

public class DdlUtil {
    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private static final String COLUMN_TYPE_LONG = " LONG";
    private static final String COLUMN_TYPE_INTEGER = " INTEGER";
    private static final String COLUMN_TYPE_DOUBLE = " DOUBLE";
    private static final String COLUMN_TYPE_BOOLEAN = " BIT";
    private static final String COLUMN_TYPE_STRING = " VARCHAR";

    /**
     * Generate SQL command for creating table
     */
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
        String columnName = getColumnName(key);

        if (key.equalsIgnoreCase(ID)) return columnName + COLUMN_TYPE_LONG + COLUMN_PRIMARY_KEY;
        return columnName + Type.getInstance(entry.getValue().getClass()).toSql();
    }

    /**
     * Supported Java DataTypes for SQL mapper
     */
    public enum Type {
        LONG {
            @Override
            String toSql() {
                return COLUMN_TYPE_LONG;
            }
        }, INTEGER {
            @Override
            String toSql() {
                return COLUMN_TYPE_INTEGER;
            }
        }, DOUBLE {
            @Override
            String toSql() {
                return COLUMN_TYPE_DOUBLE;
            }
        }, BOOLEAN {
            @Override
            String toSql() {
                return COLUMN_TYPE_BOOLEAN;
            }
        }, STRING {
            @Override
            String toSql() {
                return COLUMN_TYPE_STRING;
            }
        }, UNDEFINED {
            @Override
            String toSql() {
                return COLUMN_TYPE_STRING;
            }
        };

        private static <T> Type getInstance(Class<T> type) {
            try {
                return Type.valueOf(type.getSimpleName().toUpperCase());
            } catch (Exception e) {
                return Type.UNDEFINED;
            }
        }

        abstract String toSql();
    }
}
