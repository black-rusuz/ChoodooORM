package team.choodoo.orm.sql;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static team.choodoo.orm.sql.CommonSql.*;

// TODO: relative mapping NF3
@SuppressWarnings({"unchecked", "rawtypes"})
public class Converters {
    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    // * WRITE

    /**
     * Generates LinkedHashMap from bean
     *
     * @param bean Serializable
     * @param <T>  Generic
     * @return LinkedHashMap
     */
    public static <T> LinkedHashMap<String, Object> getMap(T bean) {
        return objectMapper.convertValue(bean, LinkedHashMap.class);
    }

    /**
     * Using if LinkedHashMap contains another LinkedHashMap
     *
     * @param map example:
     *            <code>
     *            {
     *            id: 0,
     *            name: "John Doe",
     *            position: "SEO"
     *            }
     *            </code>
     * @return example: "0::John Doe::SEO"
     */
    private static String innerMapToString(LinkedHashMap<String, Object> map) {
        return map.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining(FIELDS_DELIMITER));
    }

    /**
     * Using if LinkedHashMap contains another LinkedHashMap
     *
     * @param list example:
     *             <code>
     *             ["Jonh Doe", "Sarah Doe"]
     *             </code>
     * @return "John Doe--Sarah Doe"
     */
    private static String innerListToString(ArrayList<LinkedHashMap<String, Object>> list) {
        return list.stream()
                .map(Converters::innerMapToString)
                .collect(Collectors.joining(BEANS_DELIMITER));
    }

    /**
     * Converts object to SQL insert string
     *
     * @param value example: 0
     * @return example: "'0'"
     */
    private static String toValues(Object value) {
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueList) valueString = innerListToString(valueList);
        return String.format(SQL_VALUE_WRAPPER, valueString);
    }

    /**
     * Converts object to SQL update string
     *
     * @param entry example: {id: 0}
     * @return example: "id = '0'"
     */
    private static String toKeyValues(Map.Entry<String, Object> entry) {
        Object value = entry.getValue();
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueList) valueString = innerListToString(valueList);
        return String.format(SQL_KEY_VALUE_WRAPPER, getColumnName(entry.getKey()), valueString);
    }

    /**
     * Converts LinkedHashMap to SQL insert string
     *
     * @param map example:
     *            <code>
     *            {
     *            id: 0,
     *            name: "John Doe",
     *            position: "SEO"
     *            }
     *            </code>
     * @return example: "'0', 'John Doe', 'SEO'"
     */
    public static String mapToValues(LinkedHashMap<String, Object> map) {
        return map.values().stream()
                .map(Converters::toValues)
                .collect(Collectors.joining(SQL_COMMA));
    }


    /**
     * Converts LinkedHashMap to SQL update string
     *
     * @param map example:
     *            <code>
     *            {
     *            id: 0,
     *            name: "John Doe",
     *            position: "SEO"
     *            }
     *            </code>
     * @return example: "id = '0', name = 'John Doe', position = 'SEO'"
     */
    public static String mapToKeyValues(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(Converters::toKeyValues)
                .collect(Collectors.joining(SQL_COMMA));
    }

    // * READ

    /**
     * Read data from ResultSet
     */
    public static <T> List<T> readData(Class<T> type, ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            list.add(readObject(type, rs));
        }
        return list;
    }

    /**
     * Read object from ResultSet
     */
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
