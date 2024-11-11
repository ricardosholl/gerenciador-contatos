import java.util.HashSet;

public class Contato {
    private String nome;
    private HashSet<String> telefones;
    private String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefones = new HashSet<>();
        this.telefones.add(telefone);
        this.email = email;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public HashSet<String> getTelefones() { return telefones; }
    public String getEmail() { return email; }
    
    @Override
    public String toString() {
        return "Contato{nome='" + nome + "', telefones=" + telefones + ", email='" + email + "'}";
    }
}