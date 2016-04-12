package dbService;

import accounts.UserProfile;
import dbService.dao.UsersHibDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexandr on 18.02.2016.
 */
public class DBHibernate implements DBService {




        private static final String hibernate_show_sql = "true";
        private static final String hibernate_hbm2ddl_auto = "update";

        private final SessionFactory sessionFactory;

        public  DBHibernate() {
            Configuration configuration =getPostgresConfiguration();
            sessionFactory = createSessionFactory(configuration);
        }

        @SuppressWarnings("UnusedDeclaration")
        private Configuration getMySqlConfiguration() {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(UsersDataSet.class);

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
            configuration.setProperty("hibernate.connection.username", "tully");
            configuration.setProperty("hibernate.connection.password", "tully");
            configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
            configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
            return configuration;
        }

        private Configuration getH2Configuration() {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(UsersDataSet.class);

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
            configuration.setProperty("hibernate.connection.username", "tully");
            configuration.setProperty("hibernate.connection.password", "tully");
            configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
            configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
            return configuration;
        }



        private Configuration getPostgresConfiguration() {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(UsersDataSet.class);


            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/JavaDB");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "saw123");
            configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
            configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
            return configuration;
        }



        public UsersDataSet getUser(long id) throws DBException {
            try {
                Session session = sessionFactory.openSession();
                UsersHibDAO dao = new UsersHibDAO(session);
                UsersDataSet dataSet = dao.get(id);
                session.close();
                return dataSet;
            } catch (HibernateException e) {
                throw new DBException(e);
            }
        }


        public UserProfile getUser(String  login) throws DBException {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try {
                UsersHibDAO dao = new UsersHibDAO(session);
                UserProfile userProfile = dao.getUserProfile(login);
                transaction.commit();
                return userProfile;
            } catch (HibernateException e) {
                transaction.rollback();
                throw new DBException(e);
            }
            finally {

                session.close();
            }

        }




        public long addUser(UserProfile userProfile) throws ConstraintViolationException {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try {
                UsersHibDAO dao = new UsersHibDAO(session);
                long id = dao.insertUser(userProfile);
                transaction.commit();
                return id;
            }

            catch (HibernateException e) {
                if(e.getClass().equals(ConstraintViolationException.class)) throw (ConstraintViolationException)e;
                else {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
           finally {

                session.close();
            }
            return -1;
        }

        public void printConnectInfo() {
            try {
                SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
                Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
                System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver: " + connection.getMetaData().getDriverName());
                System.out.println("Autocommit: " + connection.getAutoCommit());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static SessionFactory createSessionFactory(Configuration configuration) {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.build();
            return configuration.buildSessionFactory(serviceRegistry);
        }
}


