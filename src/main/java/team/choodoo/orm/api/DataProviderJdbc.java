package team.choodoo.orm.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.sql.Action;
import team.choodoo.orm.utils.Constants;
import team.choodoo.orm.utils.ReflectUtil;

import java.util.List;

public class DataProviderJdbc implements IDataProvider {
    private final Logger log = LogManager.getLogger(this.getClass());
    private final DatabaseHelper dbHelper = new DatabaseHelper();

    public DataProviderJdbc(Object... beans) {
        dbHelper.createTables(beans);
    }

    // * ID

    protected <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return ReflectUtil.getId(oldBean) != 0;
    }

    protected <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Constants.NOT_FOUND, type.getSimpleName(), id);
    }

    protected <T> long getNewId(Class<T> type) {
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
        return list.isEmpty() ? ReflectUtil.getEmptyObject(type) : list.get(0);
    }

    @Override
    public <T> long insert(Class<T> type, T bean) {
        long id = ReflectUtil.getId(bean);
        if (hasSavedId(type, id)) {
            ReflectUtil.setId(bean, getNewId(type));
        }
        dbHelper.write(Action.INSERT, bean);
        return ReflectUtil.getId(bean);
    }

    @Override
    public <T> boolean delete(Class<T> type, long id) {
        if (!hasSavedId(type, id)) {
            log.warn(getNotFoundMessage(type, id));
            return false;
        }
        return dbHelper.write(Action.DELETE, ReflectUtil.getEmptyObject(type));
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
