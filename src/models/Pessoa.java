package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String email;
    protected TipoUsuario tipo;
    protected ImageIcon pfp=null;
    private List<Emprestimo> historicoEmprestimos;

    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.historicoEmprestimos = new ArrayList<>();

    }
    public Pessoa(String pfp,String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.historicoEmprestimos = new ArrayList<>();
        this.pfp= new ImageIcon(pfp);
    }

    public String getNome(){return nome;}
    public String getCpf(){return cpf;}
    public String getEmail(){return email;}
    public void setNome(String nome){this.nome = nome;}
    public void setCpf(String cpf){this.cpf = cpf;}
    public void setEmail(String email){this.email = email;}
    public TipoUsuario getTipo(){return tipo;}
    public void adicionarEmprestimo(Emprestimo e){historicoEmprestimos.add(e);}
    public void removerEmprestimo(Emprestimo e){historicoEmprestimos.remove(e);}
    public List<Emprestimo> getHistoricoEmprestimos(){return new ArrayList<>(historicoEmprestimos);}
    
    public int getNumeroEmprestimosAtivos() {
        int count = 0;
        for (Emprestimo e : historicoEmprestimos) {
            if (e.isAtivo()) {
                count++;
            }
        }
        return count;
    }
    
    public String exibirHistorico() {
        if (historicoEmprestimos.isEmpty()) {
            return ("Nenhum empréstimo registrado para " + nome);
        } else {
            String str="Histórico de empréstimos de " + nome + ":\n";
            for (Emprestimo e : historicoEmprestimos) {
                String status = e.isAtivo() ? " (Ativo)" : " (Finalizado)";
                str += "- " + e + status + "\n";
            }
            return str;
        }
    }

    public String toString(){
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email + ", Tipo: " + tipo;
    }

    public abstract int getLimiteEmprestimos();
}