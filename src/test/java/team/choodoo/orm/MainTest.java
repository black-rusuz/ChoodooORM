package team.choodoo.orm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.choodoo.orm.api.DataProvider;
import team.choodoo.orm.api.IDataProvider;
import team.choodoo.orm.model.TestBean;
import team.choodoo.orm.model.TestData;
import team.choodoo.orm.utils.Constants;

import java.util.List;

public class MainTest extends TestData {
    private final Logger log = LogManager.getLogger(this.getClass());
    IDataProvider dp = DataProvider.createTables(t1, d1);

    @AfterEach
    void cleanUp() {
        dp.getAll(TestBean.class).forEach(e -> dp.delete(e));
    }

    @Test
    void getAllPos() {
        t.forEach(e -> dp.insert(e));
        Assertions.assertEquals(t, dp.getAll(TestBean.class));
    }

    @Test
    void getAllNeg() {
        Assertions.assertEquals(List.of(), dp.getAll(TestBean.class));
    }

    @Test
    void getByIdPos() {
        dp.insert(t1);
        Assertions.assertEquals(t1, dp.getById(TestBean.class, t1.getId()));
    }

    @Test
    void getByIdNeg() {
        Assertions.assertNull(dp.getById(TestBean.class, t1.getId()));
    }

    @Test
    void insertPos() {
        Assertions.assertEquals(t1, dp.insert(t1));
    }

    @Test
    void insertNeg() {
        Assertions.assertNotEquals(t2, dp.insert(t1));
    }

    @Test
    void updatePos() {
        dp.insert(t1).setName(Constants.APP_NAME);
        Assertions.assertEquals(t1, dp.update(t1));
    }

    @Test
    void updateNeg() {
        Assertions.assertNull(dp.update(t1));
    }

    @Test
    void deletePos() {
        dp.insert(t1);
        Assertions.assertTrue(dp.delete(t1));
    }

    @Test
    void deleteNeg() {
        Assertions.assertFalse(dp.delete(t1));
    }
}
