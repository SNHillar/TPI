package service;

import dao.GenericDAO;
import java.util.Collections;
import java.util.List;
import models.CredencialAcceso;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioServiceImpl implements GenericService<Usuario> {

    private final GenericDAO<Usuario> usuarioDao;
    
    private final GenericDAO<CredencialAcceso> credencialAccesoDao;
    
    

    public UsuarioServiceImpl(GenericDAO<Usuario> usuarioDao, GenericDAO<CredencialAcceso> credencialAccesoDao) {
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
    public void delete(int id) throws Exception {
        if(id < 0){
            throw new IllegalArgumentException("No se puede buscar un ID negativo.");
        }
        usuarioDao.delete(id);
    }

    @Override
    public Usuario findById(int id) throws Exception {
        if(id < 0){
            throw new IllegalArgumentException("No se puede buscar un ID negativo.");
        }
        return usuarioDao.findById(id);
    }

    @Override
    public List<Usuario> findByAll() throws Exception {
        return Collections.unmodifiableList(usuarioDao.findByAll());
    }
    
    public void registrarUsuario (Usuario username, String passwordPlano) throws Exception{
        if(username.getUsername() == null || username.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de usuario no puede estar nulo ni vacio.");
        }
        if(password.getHashPassword() == null || password.getHashPassword().trim().isEmpty()){
            throw new IllegalArgumentException("El password no puede estar nulo ni vacio.");
        }
        
        
        
        usuarioDao.insert(username);
        
        // asociamos la credencial al usuario creado
        password.setUserId(username.getId());
        credencialAccesoDao.insert(password);
        
        System.out.println("Usuario ingresado con exito!");
    }
}
