package models;

public class Usuario extends Base {

    private String username;
    private String email;
    private boolean activo;
    private String fechaRegistro;
    private CredencialAcceso credencial;

    public Usuario(int id, String username, String email) {
        super(id, false);
        this.username = username;
        this.email = email;
    }

    public Usuario() {
        super();
    }

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{" 
                + "id= " + getId()
                + "username=" + username
                + ", email=" + email
                + ", activo=" + activo
                + ", fechaRegistro=" + fechaRegistro 
                + "eliminado= " + isEliminado()
                + '}';
    }

}
