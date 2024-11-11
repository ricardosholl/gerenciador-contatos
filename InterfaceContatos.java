import javax.swing.*;
import java.awt.*;

public class InterfaceContatos extends JFrame {
    private GerenciadorContatos gerenciador;
    private JTextField nomeField, telefoneField, emailField;
    private JTextArea resultadoArea;

    public InterfaceContatos() {
        gerenciador = new GerenciadorContatos();
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Gerenciador de Contatos");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        inputPanel.add(telefoneField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        // Botões
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> adicionarContato());
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> listarContatos());
        inputPanel.add(addButton);
        inputPanel.add(listarButton);

        // Área de resultados
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void adicionarContato() {
        try {
            String resultado = gerenciador.adicionarContato(
                nomeField.getText(),
                telefoneField.getText(),
                emailField.getText()
            );
            resultadoArea.setText(resultado);
            limparCampos();
        } catch (ContatoException e) {
            resultadoArea.setText("Erro: " + e.getMessage());
        }
    }

    private void listarContatos() {
        StringBuilder sb = new StringBuilder();
        for (Contato contato : gerenciador.listarContatos()) {
            sb.append(contato.toString()).append("\n");
        }
        resultadoArea.setText(sb.toString());
    }

    private void limparCampos() {
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceContatos().setVisible(true);
        });
    }
}