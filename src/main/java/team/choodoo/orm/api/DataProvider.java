package team.choodoo.orm.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.sql.Action;
import team.choodoo.orm.utils.DatabaseHelper;
import team.choodoo.orm.utils.Messages;
import team.choodoo.orm.utils.ReflectUtil;

import java.util.List;

public class DataProvider implements IDataProvider {
    private final Logger log = LogManager.getLogger(this.getClass());
    private final DatabaseHelper dbHelper = new DatabaseHelper();

    public DataProvider(Object... beans) {
        dbHelper.createTables(beans);
    }

    // * ID

    private <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return oldBean != null;
    }

    private <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Messages.NOT_FOUND, type.getSimpleName(), id);
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
        long id = ReflectUtil.getId(bean);
        if (hasSavedId(type, id)) {
            long newId = getNewId(type);
            ReflectUtil.setId(bean, newId);
        }
        dbHelper.write(Action.INSERT, bean);
        return bean;
    }

    @Override
    public <T> boolean delete(Class<T> type, long id) {
        if (!hasSavedId(type, id)) {
            log.warn(getNotFoundMessage(type, id));
            return false;
        }
        T bean = ReflectUtil.getEmptyObject(type);
        ReflectUtil.setId(bean, id);
        return dbHelper.write(Action.DELETE, bean);
    }

    @Override
    public <T> boolean update(Class<T> type, T bean) {
        long id = ReflectUtil.getId(bean);
        if (!hasSavedId(type, id)) {
            log.warn(getNotFoundMessage(type, id));
            return false;
        }
        return dbHelper.write(Action.UPDATE, bean);
    }
}
