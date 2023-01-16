package team.choodoo.orm.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.sql.Action;
import team.choodoo.orm.utils.DatabaseHelper;
import team.choodoo.orm.utils.Messages;
import team.choodoo.orm.utils.ReflectUtil;

import java.util.List;

public class DataProvider implements IDataProvider {
    private static final DatabaseHelper dbHelper = new DatabaseHelper();
    private final Logger log = LogManager.getLogger(this.getClass());

    public DataProvider() {
    }

    public static DataProvider createTables(Object... beans) {
        dbHelper.createTables(beans);
        return new DataProvider();
    }

    // * ID

    private <T> String getNotFoundMessage(T bean) {
        return String.format(Messages.NOT_FOUND, bean.toString());
    }

    private <T> long getNewId(Class<T> type) {
        return dbHelper.getMaxId(type) + 1;
    }

    // * GENERICS

    @Override
    public <T> List<T> getAll(Class<T> type) {
        return dbHelper.read(type);
    }

    @Override
    public <T> T getById(Class<T> type, long id) {
        List<T> list = dbHelper.read(type, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T> T insert(Class<T> type, T bean) {
        ReflectUtil.setId(bean, getNewId(type));
        dbHelper.write(Action.INSERT, bean);
        return bean;
    }

    @Override
    public <T> boolean delete(Class<T> type, T bean) {
        boolean result = dbHelper.write(Action.DELETE, bean) != 0;
        if (!result) log.warn(getNotFoundMessage(bean));
        return result;
    }

    @Override
    public <T> boolean update(Class<T> type, T bean) {
        boolean result = dbHelper.write(Action.UPDATE, bean) != 0;
        if (!result) log.warn(getNotFoundMessage(bean));
        return result;
    }
}
