package dao;

import java.util.List;
import java.sql.Connection;



public interface GenericDAO<T> {

    void insert(T entidad) throws Exception;
    
    //void insertTx(T entidad, Connection conn) throws Exception;

    void update(T entidad) throws Exception;

    void delete(int id) throws Exception;

    T findById(int id) throws Exception;

    List<T> findByAll();
    
}
