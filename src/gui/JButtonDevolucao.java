package gui;

import javax.swing.JButton;
import models.Emprestimo;

public class JButtonDevolucao extends JButton{
    public Emprestimo empres;

    public JButtonDevolucao(Emprestimo user){
        super();
        empres=user;
    }
}