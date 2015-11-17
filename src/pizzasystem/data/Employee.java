package pizzasystem.data;

/**
 * Representa um funcionário que pode usar o sistema.
 */
public class Employee extends Person{
    /**
     * Enumeração dos níveis de permissão do usuário.
     */
    public enum Role {

        /**
         * Administrador do sistema.
         */
        Admin,

        /**
         * Atendente da pizzaria.
         */
        Attendant,

        /**
         * Pizzaiolo.
         */
        Cook,

        /**
         * Entregador de pizzas.
         */
        Delivery
    }
    
    private String user;
    private String hashPass;
    private Role role;
    private String cpf;

    /**
     * Obtém o nome de usuário.
     * @return o usuário do funcionario
     */
    public String getUser() {
        return user;
    }

    /**
     * Atribui o nome de usuário.
     * @param user nome de usuário a ser atribuído.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Obtém a senha do usuário na forma de uma hash.
     * @return A senha hashada do usuário.
     */
    public String getHashPass() {
        return hashPass;
    }

    /**
     * Atribui a senha hashada do usuário.
     * @param hashPass A senha a ser atribuida.
     */
    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    /**
     * Obtém o nível de permissão do usuário.
     * @return retorna a função do funcionario
     */
    public Role getRole() {
        return role;
    }

    /**
     * Atribui o nível de permissão do usuário.
     * @param role nível de permissão
     */
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * Obtém o CPF
     * @return O cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Atribui o CPF
     * @param cpf O cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
