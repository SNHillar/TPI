package service;

import java.util.List;

public interface GenericService<T>{
    
    void insert(T entidad) throws Exception;
    
    void update(T entidad) throws Exception;
    
    void delete(long id) throws Exception;

    T findById(long id) throws Exception;

    List<T> findByAll() throws Exception;
    
}
