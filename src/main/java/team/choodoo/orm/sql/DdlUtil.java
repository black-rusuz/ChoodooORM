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
    private static final String COLUMN_TYPE_STRING = " VARCHAR";
    private static final String COLUMN_TYPE_INT = " INTEGER";
    private static final String COLUMN_TYPE_DOUBLE = " DOUBLE";
    private static final String COLUMN_TYPE_BOOLEAN = " BIT";

    public static <T> String createTable(T bean) {
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(SQL_CREATE_TABLE_IF_NOT_EXISTS, getTableName(bean.getClass()), mapToColumns(map));
    }

    private static String mapToColumns(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(DdlUtil::mapper)
                .collect(Collectors.joining(SQL_COMMA));
    }

    private static String mapper(Map.Entry<String, Object> entry) {
        String key = entry.getKey();

        if (key.equals(ID))
            return getColumnName(key) + COLUMN_TYPE_LONG + COLUMN_PRIMARY_KEY;

        else if (entry.getValue().getClass() == Integer.class)
            return getColumnName(key) + COLUMN_TYPE_INT;

        else if (entry.getValue().getClass() == Double.class)
            return getColumnName(key) + COLUMN_TYPE_DOUBLE;

        else if (entry.getValue().getClass() == Boolean.class)
            return getColumnName(key) + COLUMN_TYPE_BOOLEAN;

        else
            return getColumnName(key) + COLUMN_TYPE_STRING;
    }
}
