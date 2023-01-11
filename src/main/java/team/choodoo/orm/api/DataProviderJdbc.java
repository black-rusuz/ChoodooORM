package team.choodoo.orm.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.utils.ConfigurationUtil;
import team.choodoo.orm.utils.Constants;
import team.choodoo.orm.utils.JdbcUtil;
import team.choodoo.orm.utils.ReflectUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataProviderJdbc implements IDataProvider {
    private final Logger log = LogManager.getLogger(this.getClass());

    private String hostname;
    private String username;
    private String password;

    public DataProviderJdbc(Object ...beans) {
        try {
            Map<String, String> database = (Map<String, String>) ConfigurationUtil.getValue(Constants.DATABASE);
            hostname = database.get(Constants.HOSTNAME);
            username = database.get(Constants.USERNAME);
            password = database.get(Constants.PASSWORD);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Arrays.stream(beans).forEach((e) -> {
            try {
                write(JdbcUtil.createTable(e));
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        });
    }

    // READ

    private <T> List<T> read(Class<T> type) {
        return read(type, JdbcUtil.selectAllFromTable(type));
    }

    private <T> List<T> read(Class<T> type, long id) {
        return read(type, JdbcUtil.selectFromTableById(type, id));
    }

    private <T> List<T> read(Class<T> type, String sql) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            log.debug(sql);
            list = JdbcUtil.readData(type, resultSet);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    // WRITE

    private void write(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();

        log.debug(sql);
        statement.executeUpdate(sql);

        connection.close();
        statement.close();
    }

    private <T> boolean write(String methodName, T bean) {
        long id = ReflectUtil.getId(bean);
        String sql = switch (methodName) {
            case Constants.METHOD_NAME_APPEND -> JdbcUtil.insertIntoTableValues(bean);
            case Constants.METHOD_NAME_DELETE -> JdbcUtil.deleteFromTableById(bean, id);
            case Constants.METHOD_NAME_UPDATE -> JdbcUtil.updateTableSetById(bean, id);
            default -> "";
        };

        try {
            write(sql);
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // GENERICS

    @Override
    public <T> List<T> getAll(Class<T> type) {
        return read(type);
    }

    @Override
    public <T> T getById(Class<T> type, long id) {
        List<T> list = read(type, id);
        return list.isEmpty() ? ReflectUtil.getEmptyObject(type) : list.get(0);
    }

    @Override
    public <T> long insert(Class<T> type, T bean) {
        long id = ReflectUtil.getId(bean);
//        if (hasSavedId(type, id)) {
//            ReflectUtil.setId(bean, System.currentTimeMillis());
//        }
        write(Constants.METHOD_NAME_APPEND, bean);
        return ReflectUtil.getId(bean);
    }

    @Override
    public <T> boolean delete(Class<T> type, long id) {
//        if (!hasSavedId(type, id)) {
//            log.warn(getNotFoundMessage(type, id));
//            return false;
//        }
        return write(Constants.METHOD_NAME_DELETE, ReflectUtil.getEmptyObject(type));
    }

    @Override
    public <T> boolean update(Class<T> type, T bean) {
        long id = ReflectUtil.getId(bean);
//        if (!hasSavedId(type, id)) {
//            log.warn(getNotFoundMessage(type, id));
//            return false;
//        }
        return write(Constants.METHOD_NAME_UPDATE, bean);
    }
}
