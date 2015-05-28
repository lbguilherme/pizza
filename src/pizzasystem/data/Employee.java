package pizzasystem.data;

public class Employee extends Person {
    
    public enum Role {
        Admin,
        Attendant,
        Cook,
        Delivery
    }
    
    private String user;
    private String hashPass;
    private Role role;
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
