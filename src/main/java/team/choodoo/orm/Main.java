package team.choodoo.orm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.api.DataProviderJdbc;
import team.choodoo.orm.api.IDataProvider;
import team.choodoo.orm.utils.ConfigurationUtil;
import team.choodoo.orm.utils.Constants;

import java.io.IOException;
import java.util.Objects;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info(ConfigurationUtil.getValue(Constants.APP_NAME));

        Test test = new Test(0, "Test", 2000);
        log.info(Test.class);
        log.info(test.getClass());
        log.info(Objects.equals(Test.class, test.getClass()));

        IDataProvider dp = new DataProviderJdbc(test);
        dp.insert(Test.class, test);
        log.info(test);
        log.info(dp.getAll(Test.class));
    }
}