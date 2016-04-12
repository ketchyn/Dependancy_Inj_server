package dbService.dao;

import accounts.UserProfile;
import dbService.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexandr on 12.04.2016.
 */
public class UsersJdbcDAO {
    private Executor executor;

    public UsersJdbcDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UserProfile getUser(String login) throws SQLException {
        return executor.execQuery("select * from users where login=" + login, result -> {
            result.next();
            return new UserProfile(result.getString("email"), result.getString("login"), result.getString("password"));
        });
    }

    public long getUserId(UserProfile profile) throws SQLException {
        return executor.execQuery("select * from users where login='" + profile.getLogin() + "'", result -> {
            result.next();
            return result.getLong("id");
        });
    }

    public void insertUser(UserProfile profile) throws SQLException {
        executor.execUpdate("insert into users  values ('" + profile.getEmail() + "'),('" + profile.getLogin() + "'),('" + profile.getPass() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, email varchar(256),login varchar(256),password varchar(256) primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
