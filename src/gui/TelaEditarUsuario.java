package gui;

import controllers.Biblioteca;
import exceptions.UsuarioNaoCadastradoException;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaEditarUsuario extends JFrame {
    private JTextField campoCpfBusca;
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoExtra;
    private JLabel labelExtra;

    private Biblioteca biblioteca;

    public TelaEditarUsuario(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setTitle("Editar Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        campoCpfBusca = new JTextField();
        campoNome = new JTextField();
        campoEmail = new JTextField();
        campoExtra = new JTextField();
        labelExtra = new JLabel("Extra: ");

        JButton btnCarregar = new JButton("Buscar");
        btnCarregar.addActionListener(this::buscarUsuario);

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.addActionListener(this::salvarAlteracoes);

        add(new JLabel("CPF do usuário:"));
        add(campoCpfBusca);
        add(btnCarregar);
        add(new JLabel()); // espaço vazio

        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Email:"));
        add(campoEmail);
        add(labelExtra);
        add(campoExtra);
        add(btnSalvar);
        add(new JLabel());

        setVisible(true);
    }

    private void buscarUsuario(ActionEvent e) {
        String cpf = campoCpfBusca.getText().trim();
        try {
            Pessoa p = biblioteca.buscarUsuarioExato(cpf);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
                return;
            }
            campoNome.setText(p.getNome());
            campoEmail.setText(p.getEmail());
            if (p instanceof Aluno) {
                campoExtra.setText(((Aluno) p).getMatricula());
                labelExtra.setText("Matrícula:");
            } else if (p instanceof Professor) {
                campoExtra.setText(((Professor) p).getDepartamento());
                labelExtra.setText("Departamento:");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar usuário: " + ex.getMessage());
        }
    }

    private void salvarAlteracoes(ActionEvent e) {
        String cpf = campoCpfBusca.getText().trim();
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String extra = campoExtra.getText().trim();
        try {
            biblioteca.editarUsuario(cpf, nome, email, extra);
            JOptionPane.showMessageDialog(this, "Usuário editado com sucesso.");
            dispose();
        } catch (UsuarioNaoCadastradoException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}
