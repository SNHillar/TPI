package service;


import dao.CredencialAccesoDAO;
import java.util.List;
import models.CredencialAcceso;

public class CredencialAccesoServiceImpl implements GenericService<CredencialAcceso>{
    
    // instanciamos un DAO
    
    private final CredencialAccesoDAO credencialAccesoDao;
    
    private final CredencialAccesoServiceImpl credencialAccesoServiceImpl;

    public CredencialAccesoServiceImpl(CredencialAccesoDAO credencialAccesoDao, CredencialAccesoServiceImpl credencialAccesoServiceImpl) {
        if(credencialAccesoDao == null){
            throw new IllegalArgumentException("CrecencialAccesoDAO no puede ser null");
        }
        if(credencialAccesoDao == null){
            throw new IllegalArgumentException("CredencialAccesoServiceImpl no puede ser null");
        }
        this.credencialAccesoDao = credencialAccesoDao;
        
        this.credencialAccesoServiceImpl = credencialAccesoServiceImpl;
    }
    
    

    @Override
    public void insert(CredencialAcceso entidad) throws Exception {
        
        credencialAccesoServiceImpl.insert(entidad.getHashPassword());
    }

    @Override
    public void update(CredencialAcceso entidad) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CredencialAcceso findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CredencialAcceso> findByAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void passwordValidator(CredencialAcceso password) throws Exception{
        if(password == null) {
            throw new IllegalArgumentException("La contrasenia no puede ser null");
        }
        if(password.getHashPassword() == null || password.getHashPassword().trim().isEmpty()){
            throw new IllegalArgumentException("El hashpassword no puede estar vacio.");
        }
        if(password.getSalt() == null || password.getSalt().trim().isEmpty()){
            throw new IllegalArgumentException("El Salt no puede estar vacio.");
        }
    }
    
}
