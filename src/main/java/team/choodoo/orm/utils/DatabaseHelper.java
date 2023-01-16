package team.choodoo.orm.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.choodoo.orm.sql.Action;
import team.choodoo.orm.sql.Converters;
import team.choodoo.orm.sql.DdlUtil;
import team.choodoo.orm.sql.DmlUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static team.choodoo.orm.sql.DmlUtil.selectMaxIdFromTable;

public class DatabaseHelper {
    private final Logger log = LogManager.getLogger(this.getClass());

    private String hostname;
    private String username;
    private String password;

    public DatabaseHelper() {
        try {
            Map<String, String> config = (Map<String, String>) Config.getValue(Constants.DATABASE);
            hostname = config.get(Constants.HOSTNAME);
            username = config.get(Constants.USERNAME);
            password = config.get(Constants.PASSWORD);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    // * CREATE TABLES
    // TODO: migration variants
    public void createTables(Object... beans) {
        Arrays.stream(beans).forEach((e) -> {
            try {
                write(DdlUtil.createTable(e));
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        });
    }

    // * READ

    public <T> List<T> read(Class<T> type) {
        return read(type, DmlUtil.selectAllFromTable(type));
    }

    public <T> List<T> read(Class<T> type, long id) {
        return read(type, DmlUtil.selectFromTableById(type, id));
    }

    private <T> List<T> read(Class<T> type, String sql) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            log.debug(sql);
            list = Converters.readData(type, resultSet);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    public <T> long getMaxId(Class<T> type) {
        long maxId = 0;
        String sql = selectMaxIdFromTable(type);
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            log.debug(sql);
            if (resultSet.next()) maxId = resultSet.getLong(1);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return maxId;
    }

    // * WRITE

    private int write(String sql) throws SQLException {
        int resultCode;
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();

        log.debug(sql);
        resultCode = statement.executeUpdate(sql);

        connection.close();
        statement.close();
        return resultCode;
    }

    public <T> int write(Action action, T bean) {
        long id = ReflectUtil.getId(bean);
        String sql = switch (action) {
            case INSERT -> DmlUtil.insertIntoTableValues(bean);
            case DELETE -> DmlUtil.deleteFromTableById(bean, id);
            case UPDATE -> DmlUtil.updateTableSetById(bean, id);
        };

        try {
            return write(sql);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return 0;
        }
    }
}
