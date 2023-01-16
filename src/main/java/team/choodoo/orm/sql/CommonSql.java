package team.choodoo.orm.sql;

import com.google.common.base.CaseFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.utils.Config;
import team.choodoo.orm.utils.Constants;

import java.io.IOException;
import java.util.Map;

public class CommonSql {
    private final Logger log = LogManager.getLogger(this.getClass());

    public static final String SQL_COMMA = ", ";
    public static final String SQL_VALUE_WRAPPER = "'%s'";
    public static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";

    private static String TABLE_PREFIX = "T_";
    private static String COLUMN_PREFIX = "C_";
    public static String ID = "id";
    public static String BEANS_DELIMITER = "--";
    public static String FIELDS_DELIMITER = "::";

    public CommonSql() {
        try {
            Map<String, String> config = (Map<String, String>) Config.getValue(Constants.CONFIGURATION);
            TABLE_PREFIX = config.get(Constants.TABLE_PREFIX);
            COLUMN_PREFIX = config.get(Constants.COLUMN_PREFIX);
            ID = config.get(Constants.ID_FIELD);
            BEANS_DELIMITER = config.get(Constants.BEANS_DELIMITER);
            FIELDS_DELIMITER = config.get(Constants.FIELDS_DELIMITER);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Generates table name. <code>TABLE_PREFIX</code> is using to avoid creating a table with reserved words.
     *
     * @param type example: MyBean.class
     * @param <T>  generic
     * @return example: "T_MY_BEAN"
     */
    public static <T> String getTableName(Class<T> type) {
        String snakeCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, type.getSimpleName());
        return TABLE_PREFIX + snakeCase;
    }

    /**
     * Generates column name. <code>COLUMN_PREFIX</code> is using to avoid creating a column with reserved words.
     *
     * @param key from LinkedHashMap<String, Object>
     *            example: myField
     * @return example: "C_MY_FIELD"
     */
    public static String getColumnName(String key) {
        String snakeCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, key);
        return COLUMN_PREFIX + snakeCase;
    }

    /**
     * Simple getter for ID field
     *
     * @return "C_ID"
     */
    public static String getIdColumnName() {
        return getColumnName(ID);
    }

    /**
     * Getter for field name by its column name
     *
     * @param columnName example: "C_MY_FIELD"
     * @return example: "myField"
     */
    public static String getFieldName(String columnName) {
        String noPrefix = columnName.replace(COLUMN_PREFIX, "");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, noPrefix);
    }
}
