package team.choodoo.orm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationUtil {
    // TODO: Constant, name
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/config.yaml";
    private static Map<String, Object> map = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ConfigurationUtil.class);

    public ConfigurationUtil() {
    }

    private static Map<String, Object> getConfiguration() throws IOException {
        if (map.isEmpty()) loadConfiguration();
        return map;
    }

    private static void loadConfiguration() throws IOException {
        File nf = new File(DEFAULT_CONFIG_PATH);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        map = om.readValue(nf, map.getClass());
    }

    public static Object getValue(String key) throws IOException {
        return getConfiguration().get(key);
    }
}
