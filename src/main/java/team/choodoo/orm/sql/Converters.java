package team.choodoo.orm.sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import team.choodoo.orm.utils.Constants;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static team.choodoo.orm.sql.CommonSql.*;

// TODO: relative mapping NF3
@SuppressWarnings({"unchecked", "rawtypes"})
public class Converters {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // * WRITE

    public static <T> LinkedHashMap<String, Object> getMap(T bean) {
        return objectMapper.convertValue(bean, LinkedHashMap.class);
    }

    private static String innerMapToString(LinkedHashMap<String, Object> map) {
        return map.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining(Constants.FIELDS_DELIMITER));
    }

    private static String innerListToString(ArrayList<LinkedHashMap<String, Object>> list) {
        return list.stream()
                .map(Converters::innerMapToString)
                .collect(Collectors.joining(Constants.BEANS_DELIMITER));
    }

    private static String toValues(Object value) {
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueList) valueString = innerListToString(valueList);
        return String.format(SQL_VALUE_WRAPPER, valueString);
    }

    private static String toKeyValues(Map.Entry<String, Object> entry) {
        Object value = entry.getValue();
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueList) valueString = innerListToString(valueList);
        return String.format(SQL_KEY_VALUE_WRAPPER, getColumnName(entry.getKey()), valueString);
    }

    public static String mapToValues(LinkedHashMap<String, Object> map) {
        return map.values().stream()
                .map(Converters::toValues)
                .collect(Collectors.joining(SQL_COMMA));
    }

    public static String mapToKeyValues(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(Converters::toKeyValues)
                .collect(Collectors.joining(SQL_COMMA));
    }

    // * READ

    public static <T> List<T> readData(Class<T> type, ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            list.add(readObject(type, rs));
        }
        return list;
    }

    private static <T> T readObject(Class<T> type, ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        Map<String, Object> fieldsMap = new HashMap();

        for (int i = 1; i <= columnCount; i++) {
            String fieldName = getFieldName(md.getColumnName(i));
            Object fieldValue = rs.getObject(i);
            fieldsMap.put(fieldName, fieldValue);
        }

        return objectMapper.convertValue(fieldsMap, type);
    }
}
