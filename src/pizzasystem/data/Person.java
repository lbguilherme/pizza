package pizzasystem.data;

/**
 * Representa um potencial cliente.
 */
public class Person {
    private String name;
    private String address;
    private String phoneNumber;
    private String cep;

    /**
     * Obtém o número de telefone da pessoa, que serve como identificador.
     * @return o numero de telefone da pessoa
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Atribui o número de telefone da pessoa
     * @param phoneNumber Número de telefone
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Obtém o CEP do endereço
     * @return retorna o cep da pessoa
     */
    public String getCep() {
        return cep;
    }

    /**
     * Atribui o CEP do endereço
     * @param cep o CEP
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Obtém o nome da pessoa.
     * @return retorna o nome da pessoa.
     */
    public String getName() {
        return name;
    }

    /**
     * Atribui o nome da pessoa.
     * @param name nome da pessoa.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o endereço da pessoa
     * @return retorna o endereço da pessoa
     */
    public String getAddress() {
        return address;
    }

    /**
     * Atribui o endereço da pessoa
     * @param address endereço
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
