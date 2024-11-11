import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class GerenciadorContatos {
    private ArrayList<Contato> listaContatos;
    private TreeMap<String, Contato> contatosPorNome;
    private HashSet<String> todosTelefones;

    public GerenciadorContatos() {
        this.listaContatos = new ArrayList<>();
        this.contatosPorNome = new TreeMap<>();
        this.todosTelefones = new HashSet<>();
    }

    public String adicionarContato(String nome, String telefone, String email) throws ContatoException {
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            throw new ContatoException("Nome não pode ser vazio");
        }
        if (!validarTelefone(telefone)) {
            throw new ContatoException("Telefone inválido");
        }
        if (!validarEmail(email)) {
            throw new ContatoException("Email inválido");
        }

        // Verifica se o telefone já existe
        if (todosTelefones.contains(telefone)) {
            return "Telefone já cadastrado";
        }

        // Cria e adiciona o novo contato
        Contato novoContato = new Contato(nome, telefone, email);
        listaContatos.add(novoContato);
        contatosPorNome.put(nome, novoContato);
        todosTelefones.add(telefone);

        return "Contato adicionado com sucesso";
    }

    public Contato buscarPorNome(String nome) {
        return contatosPorNome.get(nome);
    }

    public Contato buscarPorTelefone(String telefone) {
        for (Contato contato : listaContatos) {
            if (contato.getTelefones().contains(telefone)) {
                return contato;
            }
        }
        return null;
    }

    public boolean removerContato(String nome) {
        Contato contato = contatosPorNome.remove(nome);
        if (contato != null) {
            listaContatos.remove(contato);
            todosTelefones.removeAll(contato.getTelefones());
            return true;
        }
        return false;
    }

    public List<Contato> listarContatos() {
        return new ArrayList<>(contatosPorNome.values());
    }

    private boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{10,11}");
    }

    private boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}