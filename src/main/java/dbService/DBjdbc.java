package dbService;

import accounts.UserProfile;
import dbService.dao.UsersJdbcDAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Alexandr on 12.04.2016.
 */
public class DBjdbc implements DBService {

    private final Connection connection;

    public DBjdbc() {

        this.connection = getPostgresConnection();
    }



    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=tully&").          //login
                    append("password=tully");       //password

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getPostgresConnection(){
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:postgresql://").        //db type
                    append("localhost:").           //host name
                    append("5432/").                //port
                    append("JavaDB?").          //db name
                    append("user=postgres&").          //login
                    append("password=saw123");       //password

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public UserProfile getUser(String login) throws DBException {
        try {
            return (new   UsersJdbcDAO(connection).getUser(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }


    }

    @Override
    public long addUser(UserProfile userProfile) {
        try {
            connection.setAutoCommit(false);
            UsersJdbcDAO dao = new UsersJdbcDAO(connection);
            dao.createTable();
            dao.insertUser(userProfile);
            connection.commit();
            return dao.getUserId(userProfile);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }

        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
        return -1;
    }
}


