package gui;

import javax.swing.JButton;
import models.Pessoa;

public class JButtonHistorico extends JButton{
    public Pessoa user;

    public JButtonHistorico(Pessoa user){
        super();
        this.user=user;
    }
    
    public String activate(){
        return user.exibirHistorico();
    }
}