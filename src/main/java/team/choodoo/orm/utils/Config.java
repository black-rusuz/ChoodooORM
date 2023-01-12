package team.choodoo.orm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static team.choodoo.orm.utils.Constants.DEFAULT_CONFIG_PATH;

public class Config {
    private static final Map<String, Object> config = new HashMap<>();

    public Config() {
    }

    private static void loadConfig() throws IOException {
        File nf = new File(DEFAULT_CONFIG_PATH);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        config.putAll(om.readValue(nf, config.getClass()));
    }

    private static Map<String, Object> getConfig() throws IOException {
        if (config.isEmpty()) loadConfig();
        return config;
    }

    public static Object getValue(String key) throws IOException {
        return getConfig().get(key);
    }
}
