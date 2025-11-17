package dao;

import java.util.List;



public interface GenericDAO<T> {

    void insert(T entidad) throws Exception;
    
    //void insertTx(T entidad, Connection conn) throws Exception;

    void update(T entidad) throws Exception;

    void delete(long id) throws Exception;

    T findById(long id) throws Exception;

    List<T> findByAll() throws Exception;
    
}
