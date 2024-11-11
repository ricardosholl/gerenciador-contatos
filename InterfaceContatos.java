import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceContatos extends JFrame {
    private GerenciadorContatos gerenciador;
    private JTextField nomeField, telefoneField, emailField;
    private JTextArea resultadoArea;

    public InterfaceContatos() {
        gerenciador = new GerenciadorContatos();
        configurarLookAndFeel();
        configurarInterface();
    }

    private void configurarLookAndFeel() {
        try {
            // Usando um Look and Feel mais moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Customização das cores
            UIManager.put("Panel.background", new Color(240, 240, 240));
            UIManager.put("TextField.background", Color.WHITE);
            
            // Customização de fontes
            Font mainFont = new Font("Arial", Font.PLAIN, 12);
            UIManager.put("TextField.font", mainFont);
            UIManager.put("Label.font", mainFont);
            UIManager.put("Button.font", mainFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarInterface() {
        setTitle("Gerenciador de Contatos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Painel principal com padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Dados do Contato"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos de entrada
        nomeField = new JTextField(20);
        telefoneField = new JTextField(20);
        emailField = new JTextField(20);

        // Configuração dos campos
        configurarCampo(inputPanel, "Nome:", nomeField, gbc, 0);
        configurarCampo(inputPanel, "Telefone:", telefoneField, gbc, 1);
        configurarCampo(inputPanel, "Email:", emailField, gbc, 2);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton addButton = new JButton("Adicionar");
        JButton listarButton = new JButton("Listar");
        JButton limparButton = new JButton("Limpar");
        
        // Estilização dos botões
        configurarBotao(addButton);
        configurarBotao(listarButton);
        configurarBotao(limparButton);
        
        // Listeners dos botões
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarContato();
            }
        });
        
        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarContatos();
            }
        });
        
        limparButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(listarButton);
        buttonPanel.add(limparButton);

        // Adiciona o painel de botões ao inputPanel
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        // Área de resultados
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setBackground(new Color(252, 252, 252));
        
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // Montagem final
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Centraliza a janela
        setLocationRelativeTo(null);
    }

    private void configurarCampo(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.9;
        field.setPreferredSize(new Dimension(200, 25));
        panel.add(field, gbc);
    }

    private void configurarBotao(JButton button) {
        button.setPreferredSize(new Dimension(100, 30));
        button.setFocusPainted(false);
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceContatos().setVisible(true);
            }
        });
    }
}
