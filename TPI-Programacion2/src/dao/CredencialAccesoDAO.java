package dao;

import java.util.List;
import java.sql.SQLException;
import models.CredencialAcceso;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import config.DatabaseConnectionPool;
import models.Usuario;


public class CredencialAccesoDAO implements GenericDAO<CredencialAcceso>{
    
    
    
    private static final String INSERT_SQL = "INSERT INTO credencial_acceso (hash_password, salt, user_id) VALUES (?, ?, ?);"; // los otros atributos, tienen default en la base de datos
    
    private static final String UPDATE_SQL = "UPDATE credencial_acceso SET hash_password = ?, salt = ?, last_change = NOW(), require_reset = FALSE"
            + "WHERE user_id = ?";
    
    
    private static final String DELETE_SQL = "DELET FROM credencial_acceso WHERE id = ?";
    
    private static final String SELECT_BY_ID = "SELECT * FROM credencial_acceso WHERE user_id = ?";
    
    private static final String SELECT_BY_USERNAME = "SELECT c.id, c.hash_password , c.salt, c.user_id"
            + " FROM credencial_acceso c"
            + " JOIN usuario u ON c.user_id = u.id"
            + " WHERE u.username = ? AND u.eliminado = FALSE";
    
    
    
    
    @Override
    public void insert(CredencialAcceso entidad) throws SQLException {
        
        try(Connection conn = DatabaseConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
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
        try(Connection conn = DatabaseConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)){
            stmt.setString(1, entidad.getHashPassword());
            stmt.setString(2, entidad.getSalt());
            stmt.setInt(3, entidad.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected == 0){
                throw new SQLException ("Falló la actualizacion de la contrasenia.");
            }
        } catch (SQLException e){
            System.out.println("Error, no se pudo actualizar la contrasenia." + e.getMessage());
            throw e;
        }
    }

    
    // utilizamos ON DELETE CASCADE en la base de datos, si se borra un usuario, se borra su credencial
    @Override
    public void delete(int id) throws SQLException {
        // se implementa el metodo para que compile, pero el Service no va a hacer uso porque lo maneja la BD.
        try(Connection conn = DatabaseConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)){
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected == 0){
                throw new SQLException ("No se pudo eliminar la credencial con id: " + id);
            }
        } catch (SQLException e){
            System.out.println("Error en credencialAccesoDAO.delete()" + e.getMessage());
            throw e;
        }
    }
    
    @Override
    public CredencialAcceso findById(int id) throws SQLException {
        // no tiene funcionalidad pero implementamos porque es inofensivo el metodo, solo para cumplir contrato de interfaz
        try(Connection conn = DatabaseConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)){
            stmt.setInt(1, id);
            
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    CredencialAcceso credencial = new CredencialAcceso();
                    credencial.setId(rs.getInt("id"));
                    credencial.setHashPassword(rs.getString("hash_password"));
                    credencial.setSalt(rs.getString("salt"));
                    credencial.setUserId(rs.getInt("user_id"));
                    return credencial;
                }
            }
        }
        return null;
    }

    @Override
    public List<CredencialAcceso> findByAll() throws IllegalArgumentException{
        // NO queremos listar las contraseñas, no tiene ninguna finalidad, y listar 500.000 hashes pueden generar problemas de rendimiento, etc
        throw new IllegalArgumentException("No se permite obtener un listado con las contraseñas.");
    }

    
    
    public CredencialAcceso findByUsername(String username){
        return null;
    }
    
    private final CredencialAccesoDAO credencialAccesoDao;

    public CredencialAccesoDAO() {
        
    }
    
    
}
