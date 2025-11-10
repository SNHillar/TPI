package dao;

import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
import config.DatabaseConnection;
import dao.GenericDAO;
import java.util.List;
import models.Usuario;

public class UsuarioDAO implements GenericDAO<Usuario> {
    
    private static final String INSERT_SQL = "INSERT INTO usuario (username, email) VALUES (?, ?)";
    
    private static final String UPDATE_SQL = "UPDATE usuario SET username = ?, email = ?, fecha_registro = ?, activo = ? WHERE id = ?";;
            
    private static final String DELETE_SQL = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";
    
    private static final String SELECT_BY_ID_SQL = "SELECT u.id, u.username, u.email, u.fecha_registro " +
            "c.id AS cred_id, c.hash_password " +
            "FROM usuario u LEFT JOIN credencial_acceso c ON c.user_id = u.id " +
            "WHERE u.id = ? AND u.eliminado = FALSE";
    
    private static final String SELECT_BY_ALL = "SELECT u.id, u.username, u.email, u.fecha_registro " +
            "c.id AS cred_id, c.hash_password " +
            "FROM usuario u LEFT JOIN credencial_acceso c ON c.user_id = u.id " +
            "WHERE u.eliminado = FALSE";
    
    
    
    @Override
    public void insert(Usuario entidad) throws Exception {
        try(Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
            
            
        }
    }

    @Override
    public void update(Usuario entidad) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> findByAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void restore(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
