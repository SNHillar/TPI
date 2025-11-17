
package service;

import models.CredencialAcceso;
import models.Usuario;

public interface CredencialAccesoService extends GenericService<CredencialAcceso>{
    
    Usuario login(String username, String passwordPlano) throws Exception;
    
    void changePassword(long userId, String passwordNuevo) throws Exception;
}
