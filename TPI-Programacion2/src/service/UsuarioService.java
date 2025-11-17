/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import models.Usuario;

/**
 *
 * @author saulh
 */
public interface UsuarioService extends GenericService<Usuario>{
    
    void registrarUsuario(Usuario user, String passwordPlano) throws Exception;
    
    public Usuario findByEmail(String email) throws Exception;
    
    void restore(long id) throws Exception;
}
