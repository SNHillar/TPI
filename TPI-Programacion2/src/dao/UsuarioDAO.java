
package dao;

import models.Usuario;
import java.sql.Connection;
import java.sql.SQLException;


public interface UsuarioDAO extends GenericDAO<Usuario>{
    public void insert(Connection conn, Usuario user) throws SQLException;
}
