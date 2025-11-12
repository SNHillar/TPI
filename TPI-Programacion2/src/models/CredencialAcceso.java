package models;

public class CredencialAcceso extends Base {

    private String hashPassword;
    private String salt;
    private String lastChange;
    private String requireReset;
    private int userId;

    public CredencialAcceso(int id, boolean eliminado, String hashPassword) {
        super(id, false);
        this.hashPassword = hashPassword;
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

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public String getRequireReset() {
        return requireReset;
    }

    public void setRequireReset(String requireReset) {
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
