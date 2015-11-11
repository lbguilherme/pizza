package pizzasystem.data;

/**
 *
 * @author Gabe
 */
public class Employee extends Person{

    /**
     *
     */
    public enum Role {

        /**
         *Administrador do sistema
         */
        Admin,

        /**
         *Atendente da pizzaria
         */
        Attendant,

        /**
         *Pizzaiolo
         */
        Cook,

        /**
         *Entregador de pizzas
         */
        Delivery
    }
    
    private String user;
    private String hashPass;
    private Role role;
    private String cpf;

    /**
     *
     * @return o usuario do funcionario
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return o password do usuario
     */
    public String getHashPass() {
        return hashPass;
    }

    /**
     *
     * @param hashPass
     */
    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    /**
     *
     * @return retorna a função do funcionario
     */
    public Role getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
