
package dao;

import java.util.List;
import models.CredencialAcceso;
import java.sql.SQLException;

public interface CredencialAccesoDAO extends GenericDAO<CredencialAcceso>{
    
    List<CredencialAcceso> findByUsername(String filtro) throws SQLException;
    
    CredencialAcceso findByExactUsername(String username) throws SQLException;
    
}
