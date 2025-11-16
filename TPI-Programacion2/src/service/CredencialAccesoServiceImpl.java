package service;


import dao.CredencialAccesoDAO;
import dao.UsuarioDAO;
import java.util.Collections;
import java.util.List;
import models.CredencialAcceso;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class CredencialAccesoServiceImpl implements GenericService<CredencialAcceso> {

    // instanciamos un DAO
    private final CredencialAccesoDAO credencialAccesoDao;
    
    private final UsuarioDAO usuarioDao;

    public CredencialAccesoServiceImpl(CredencialAccesoDAO credencialAccesoDao, UsuarioDAO usuarioDao) {
        if(credencialAccesoDao == null){
            throw new IllegalArgumentException("CrecencialAccesoDAO no puede ser null");
        }
        this.credencialAccesoDao = credencialAccesoDao;
        this.usuarioDao = usuarioDao;
    }

    @Override
    public void insert(CredencialAcceso entidad) throws Exception {
        passwordValidator(entidad);
        credencialAccesoDao.insert(entidad);
    }

    @Override
    public void update(CredencialAcceso entidad) throws Exception {
        passwordValidator(entidad);
        credencialAccesoDao.update(entidad);
    }

    @Override
    public void delete(int id) throws Exception {
        if(id < 0){
            throw new IllegalArgumentException("No se puede eliminar con un id negativo");
        }
        credencialAccesoDao.delete(id);
    }

    @Override
    public CredencialAcceso findById(int id) throws Exception {
        return credencialAccesoDao.findById(id);
    }

    @Override
    public List<CredencialAcceso> findByAll() throws Exception {
        return Collections.unmodifiableList(credencialAccesoDao.findByAll());
    }

    public void passwordValidator(CredencialAcceso password) throws Exception {
        if (password == null) {
            throw new IllegalArgumentException("La contrasenia no puede ser null");
        }
        if (password.getHashPassword() == null || password.getHashPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("El hashpassword no puede estar vacio.");
        }
        if (password.getSalt() == null || password.getSalt().trim().isEmpty()) {
            throw new IllegalArgumentException("El Salt no puede estar vacio.");
        }
    }
    
    
    public Usuario login (String username, String passwordPlano) throws Exception{
        
        if(username == null || passwordPlano == null || username.trim().isEmpty() || passwordPlano.trim().isEmpty()){
            throw new IllegalArgumentException("Usuario o contrasenia no puede estar vacio ni ser null.");
        }
        
        CredencialAcceso credencial = credencialAccesoDao.findByExactUsername(username);
        
        if(credencial == null){
            throw new Exception("Error de usuario o contrasenia.");
        }
        
        if(!BCrypt.checkpw(passwordPlano, credencial.getHashPassword())){
            throw new Exception("Contrasenia incorrecta.");
        }
        
        return usuarioDao.findById(credencial.getUserId());
    }
    
    public void changePassword (long userId, String newPassword) throws Exception{
        
        if(newPassword == null || newPassword.trim().length() < 8){
            throw new IllegalArgumentException("La contrasenia debe contener al menos 8 digitos.");
        }
        
        String newSalt = BCrypt.gensalt();
        String newHash = BCrypt.hashpw(newPassword, newSalt);
        
        CredencialAcceso passwordChanged = new CredencialAcceso();
        
        passwordChanged.setUserId(userId);
        passwordChanged.setHashPassword(newHash);
        passwordChanged.setSalt(newSalt);
        
        credencialAccesoDao.update(passwordChanged);
        
    }
}
