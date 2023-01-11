package team.choodoo.orm.api;

import java.util.List;

public interface IDataProvider {

    <T> List<T> getAll(Class<T> type);

    <T> T getById(Class<T> type, long id);

    <T> long insert(Class<T> type, T bean);

    <T> boolean delete(Class<T> type, long id);

    <T> boolean update(Class<T> type, T bean);
}
