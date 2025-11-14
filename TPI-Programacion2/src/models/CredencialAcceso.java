package models;

import java.time.LocalDate;

public class CredencialAcceso extends Base {

    private String hashPassword;
    private String salt;
    private LocalDate lastChange;
    private boolean requireReset;
    private int userId;

    public CredencialAcceso(int id, boolean eliminado, String hashPassword) {
        super(id, false);
        this.hashPassword = hashPassword;
    }
    
    public CredencialAcceso() {
        // es solo para el DAO
    }

    /**
     * Constructor por defecto para crear una contraseña nueva sin ID.
     */
    protected CredencialAcceso(int id, boolean eliminado) {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDate getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    public boolean getRequireReset() {
        return requireReset;
    }

    public void setRequireReset(boolean requireReset) {
        this.requireReset = requireReset;
    }

    @Override
    public String toString() {
        return "CredencialAcceso{" 
                + "id= " + getId()
                + "hashPassword=" + hashPassword
                + ", salt=" + salt
                + ", lastChange=" + lastChange
                + ", requireReset=" + requireReset 
                + "eliminado= " + isEliminado()
                + '}';
    }

}
