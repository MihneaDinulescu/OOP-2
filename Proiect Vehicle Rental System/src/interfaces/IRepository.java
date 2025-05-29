package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    void save(T entity) throws SQLException;
    T findById(long id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(long id) throws SQLException;
}
