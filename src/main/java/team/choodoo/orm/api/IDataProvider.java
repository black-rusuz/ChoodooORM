package team.choodoo.orm.api;

import java.util.List;

public interface IDataProvider {

    <T> List<T> getAll(Class<T> type);

    <T> T getById(Class<T> type, long id);

    <T> T insert(Class<T> type, T bean);

    <T> boolean delete(Class<T> type, T bean);

    <T> boolean update(Class<T> type, T bean);
}
