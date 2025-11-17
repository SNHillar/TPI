package models;

import java.time.LocalDateTime;

public class CredencialAcceso extends Base {

    private String hashPassword;
    private String salt;
    private LocalDateTime ultimoCambio;
    private boolean requireReset;
    private long userId;

    public CredencialAcceso(String hashPassword, String salt, LocalDateTime ultimoCambio, boolean requireReset, long id, boolean eliminado) {
        super(id, false);
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.ultimoCambio = ultimoCambio;
        this.requireReset = requireReset;
    }

    public CredencialAcceso() {
        super(); // llama al constructor de Base (eliminado = false)
    }

    public long getUserId() {
        return userId;
    }
    
    

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Constructor por defecto para crear una contraseña nueva sin ID.
     */
    protected CredencialAcceso(long id, boolean eliminado) {
        super();
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    public void setLastChange(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }

    public boolean isRequiereReset() {
        return requireReset;
    }

    public void setRequireReset(boolean requireReset) {
        this.requireReset = requireReset;
    }

    @Override
    public String toString() {
        return "CredencialAcceso{" 
                + "id = " + getId()
                + "hashPassword = " + hashPassword
                + ", salt = " + salt
                + ", lastChange = " + ultimoCambio
                + ", requireReset = " + requireReset 
                + "eliminado = " + isEliminado()
                + '}';
    }

}
