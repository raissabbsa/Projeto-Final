package gui;

import controllers.Biblioteca;
import exceptions.UsuarioNaoCadastradoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import models.Pessoa;

public class TelaRemoverUsuario extends JFrame {
    private JTextField campoCpfBusca;
    private JLabel labelInfo;

    private Biblioteca biblioteca;
    private GUI gui;

    public TelaRemoverUsuario(Biblioteca biblioteca,GUI gui) {
        this.biblioteca = biblioteca;
        this.gui=gui;

        setTitle("Remover Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        campoCpfBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscarUsuario);

        labelInfo = new JLabel("Informe o CPF", SwingConstants.CENTER);

        JButton btnRemover = new JButton("Remover Usuário");
        btnRemover.addActionListener(this::removerUsuario);

        add(new JLabel("CPF do usuário:", SwingConstants.CENTER));
        add(campoCpfBusca);
        add(btnBuscar);
        add(labelInfo);
        add(btnRemover);

        setVisible(true);
    }

    private void buscarUsuario(ActionEvent e) {
        String cpf = campoCpfBusca.getText().trim();
        try {
            Pessoa p = biblioteca.buscarUsuarioExato(cpf);
            if (p == null) {
                labelInfo.setText("Usuário não encontrado.");
            } else {
                labelInfo.setText("<html>Nome: " + p.getNome() + "<br>Email: " + p.getEmail() + "<br>Tipo: " + p.getTipo() + "</html>");
            }
        } catch (UsuarioNaoCadastradoException ex) {
            labelInfo.setText("Usuário não encontrado.");
        }
    }

    private void removerUsuario(ActionEvent e) {
        String cpf = campoCpfBusca.getText().trim();
        try {
            gui.removerUsuario(cpf);
            biblioteca.removerUsuario(cpf);
            JOptionPane.showMessageDialog(this, "Usuário removido com sucesso.");
            gui.panel_usuarios.setVisible(false);
            gui.panel_usuarios.setVisible(true);
            dispose();
        } catch (UsuarioNaoCadastradoException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}