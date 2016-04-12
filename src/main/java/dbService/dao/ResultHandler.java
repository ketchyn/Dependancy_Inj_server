package dbService.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alexandr on 12.04.2016.
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
