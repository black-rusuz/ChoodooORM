package team.choodoo.orm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.utils.ConfigurationUtil;
import team.choodoo.orm.utils.Constants;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info(ConfigurationUtil.getValue(Constants.APP_NAME));
    }
}