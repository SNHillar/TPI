package dao;

import java.util.List;

public interface GenericDAO<T> {

    void insert(T entidad) throws Exception;

    void update(T entidad) throws Exception;

    void delete(int id) throws Exception;

    T findById(int id) throws Exception;

    List<T> findByAll();

    void restore(int id) throws Exception;

}
