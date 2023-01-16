package team.choodoo.orm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import team.choodoo.orm.api.DataProvider;
import team.choodoo.orm.api.IDataProvider;
import team.choodoo.orm.model.DateBean;
import team.choodoo.orm.model.TestBean;
import team.choodoo.orm.model.TestData;

import java.util.stream.Collectors;

public class MainTest extends TestData {
    private final Logger log = LogManager.getLogger(this.getClass());
    IDataProvider dp = DataProvider.createTables(t1, d1);

    @Test
    void testDate() {
        dp.insert(DateBean.class, d1);
        dp.insert(DateBean.class, d2);
        log.info(dp.getAll(DateBean.class));
    }

    @Test
    void test() {
        dp.insert(TestBean.class, t1);
        dp.insert(TestBean.class, t2);
        dp.insert(TestBean.class, t3);

        dp.delete(TestBean.class, t1);

        log.info(dp.getById(TestBean.class, t1.getId()));
        log.info(dp.getById(TestBean.class, t2.getId()));
        log.info(dp.getById(TestBean.class, t3.getId()));

        log.info(dp.getAll(TestBean.class).stream().map(Object::toString).collect(Collectors.joining("\n")));
    }
}
