package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario extends Base {

    private String username;
    private String email;
    private boolean activo;
    private LocalDateTime fechaRegistro;
    private CredencialAcceso credencial;

 // Constructor principal
    public Usuario(long id, String username, String email) {
        super(id, false);
        this.username = username;
        this.email = email;
    }

// Constructor vacío (necesario para DAO)
    public Usuario() {
        super();
    }

    
 // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime  getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public CredencialAcceso getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }
    
    @Override
    public String toString() {
        return "Usuario{" 
                + "id = " + getId()
                + "username = " + username
                + ", email = " + email
                + ", activo = " + activo
                + ", fechaRegistro = " + fechaRegistro 
                + "eliminado = " + isEliminado()
                + ", credencial = " + (credencial != null ? credencial.getId() : "null")
                + '}';
    }


}
