package service;

import config.DatabaseConnectionPool;
import dao.CredencialAccesoDAO;
import dao.UsuarioDAO;
import java.util.Collections;
import java.util.List;
import models.CredencialAcceso;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDao;
    
    private final CredencialAccesoDAO credencialAccesoDao;
    
    

    public UsuarioServiceImpl(UsuarioDAO usuarioDao, CredencialAccesoDAO credencialAccesoDao) {
        this.usuarioDao = usuarioDao;
        
        this.credencialAccesoDao = credencialAccesoDao;
    }
    

    @Override
    public void insert(Usuario entidad) throws Exception {

        if (entidad.getUsername() == null || entidad.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacio ni ser nulo.");
        }
        if (entidad.getEmail() == null || entidad.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo ni esta vacio.");
        }
        if (entidad.isEliminado()) {
            throw new IllegalArgumentException("El usuario ingresado se encuentra eliminado, puede restaurarlo.");
        }
        System.out.println("Insertando usuario: " + entidad.getUsername());
        usuarioDao.insert(entidad);
    }

    @Override
    public void update(Usuario entidad) throws Exception {
        if (entidad.getUsername() == null || entidad.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacio ni ser nulo.");
        }
        if (entidad.getEmail() == null || entidad.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo ni esta vacio.");
        }
        usuarioDao.update(entidad);
    }

    @Override
    public void delete(long id) throws Exception {
        if(id < 0){
            throw new IllegalArgumentException("No se puede buscar un ID negativo.");
        }
        usuarioDao.delete(id);
    }

    @Override
    public Usuario findById(long id) throws Exception {
        if(id < 0){
            throw new IllegalArgumentException("No se puede buscar un ID negativo.");
        }
        return usuarioDao.findById(id);
    }

    @Override
    public List<Usuario> findByAll() throws Exception {
        return Collections.unmodifiableList(usuarioDao.findByAll());
    }
    
    @Override
    public void registrarUsuario (Usuario user, String passwordPlano) throws SQLException{
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de usuario no puede estar nulo ni vacio.");
        }
        if(passwordPlano == null || passwordPlano.length() < 8){
            throw new IllegalArgumentException("El password no puede estar nulo y debe contener al menos 8 digitos.");
        }
        
        String newSalt = BCrypt.gensalt();
        String newHash = BCrypt.hashpw(passwordPlano, newSalt);
        
        CredencialAcceso credencial = new CredencialAcceso();
        credencial.setSalt(newSalt);
        credencial.setHashPassword(newHash);
        
        Connection conn = null;
        try {
            conn = DatabaseConnectionPool.getConnection();
            conn.setAutoCommit(false);
            
            // DAO del user inserta al usuario
            usuarioDao.insert(conn, user);
            
            //DAO de credencial insert credencial
            credencial.setUserId(user.getId());
            credencialAccesoDao.insert(conn, credencial);
            
            conn.commit();
            
        } catch (SQLException e) {
            // si algo falla, hacemos rollback, tratamos las transacciones como atomicas
            if(conn != null){
                conn.rollback();
            }
            System.out.println("Error al registrar al usuario." + e.getMessage());
            throw e;
        }finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
