package team.choodoo.orm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import team.choodoo.orm.api.DataProviderJdbc;
import team.choodoo.orm.api.IDataProvider;
import team.choodoo.orm.model.TestBean;
import team.choodoo.orm.model.TestData;

import java.util.stream.Collectors;

public class MainTest extends TestData {
    private final Logger log = LogManager.getLogger(this.getClass());

    @Test
    void test() {
        IDataProvider dp = new DataProviderJdbc(t1);

        dp.insert(TestBean.class, t1);
        dp.insert(TestBean.class, t2);
        dp.insert(TestBean.class, t3);
        dp.insert(TestBean.class, t4);
        dp.insert(TestBean.class, t5);
        dp.insert(TestBean.class, t6);

        log.info(dp.getById(TestBean.class, t1.getId()));
        log.info(dp.getById(TestBean.class, t2.getId()));
        log.info(dp.getById(TestBean.class, t3.getId()));
        log.info(dp.getById(TestBean.class, t4.getId()));
        log.info(dp.getById(TestBean.class, t5.getId()));
        log.info(dp.getById(TestBean.class, t6.getId()));

        log.info(dp.getAll(TestBean.class).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }
}
