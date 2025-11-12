package dao;

import java.util.List;
import java.sql.SQLException;
import models.CredencialAcceso;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import config.DatabaseConnection;
import models.Usuario;


public class CredencialAccesoDAO implements GenericDAO<CredencialAcceso>{
    
    
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    private static final String INSERT_SQL = "INSERT INTO credencial_acceso (hash_password, salt, user_id) VALUES (?, ?, ?);"; // los otros atributos, tienen default en la base de datos
    
    private static final String UPDATE_SQL = "UPDATE credencial_acceso SET hash_password = ?, salt = ?, last_change = NOW(), require_reset = FALSE"
            + "WHERE user_id = ?";
    
    private static final String SELECT_BY_ID = "SELECT * FROM credencial_acceso WHERE user_id = ?";
    
    private static final String SELECT_BY_USERNAME = "SELECT c.id, c.hash_password , c.salt, c.user_id"
            + " FROM credencial_acceso c"
            + " JOIN usuario u ON c.user_id = u.id"
            + " WHERE u.username = ? AND u.eliminado = FALSE";
    
    @Override
    public void insert(CredencialAcceso entidad) throws SQLException {
        
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, entidad.getHashPassword());
            stmt.setString(2, entidad.getSalt());
            stmt.setInt(3, entidad.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0){
                throw new SQLException ("Fallo la insercion de la credencial, 0 filas afectadas.");
            }
            try(ResultSet keys = stmt.getGeneratedKeys()){
                if(keys.next()){
                    entidad.setId(keys.getInt(1));
                } else {
                    throw new SQLException ("Se inserto la credencial, pero no se pudo obtener el id.");
                }
            }
        } catch (SQLException e){
            System.out.println("Error en CredencialAccesoDAO.insert(): " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(CredencialAcceso entidad) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    // utilizamos ON DELETE CASCADE en la base de datos, si se borra un usuario, se borra su credencial
    @Override
    public void delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CredencialAcceso findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CredencialAcceso> findByAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
