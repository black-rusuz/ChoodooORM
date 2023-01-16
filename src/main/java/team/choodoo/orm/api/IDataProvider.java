package team.choodoo.orm.api;

import java.util.List;

public interface IDataProvider {

    <T> List<T> getAll(Class<T> type);

    <T> T getById(Class<T> type, long id);

    <T> T insert(T bean);

    <T> T update(T bean);

    <T> boolean delete(T bean);
}
