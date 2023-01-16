package team.choodoo.orm.utils;

public class Constants {
    // * INTERNAL
    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/dbConfig.yaml";

    // * CONFIG KEYS
    public static final String APP_NAME = "app_name";

    public static final String DATABASE = "database";
        public static final String HOSTNAME = "hostname";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";

    public static final String CONFIGURATION = "configuration";
        public static final String TABLE_PREFIX = "table_prefix";
        public static final String COLUMN_PREFIX = "column_prefix";
        public static final String ID_FIELD = "id_field";
        public static final String BEANS_DELIMITER = "beans_delimiter";
        public static final String FIELDS_DELIMITER = "fields_delimiter";
}
