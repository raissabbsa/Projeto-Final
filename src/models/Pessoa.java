package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String email;
    protected TipoUsuario tipo;
    private List<Emprestimo> historicoEmprestimos;

    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.historicoEmprestimos = new ArrayList<>();

    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void adicionarEmprestimo(Emprestimo e) {
        historicoEmprestimos.add(e);
    }

    public void removerEmprestimo(Emprestimo e) {
        historicoEmprestimos.remove(e);
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return new ArrayList<>(historicoEmprestimos);
    }

    public void exibirHistorico() {
        if (historicoEmprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado para " + nome);
        } else {
            System.out.println("Histórico de empréstimos de " + nome + ":");
            for (Emprestimo e : historicoEmprestimos) {
                System.out.println("- " + e);
            }
        }
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email + ", Tipo: " + tipo;
    }

    public abstract int getLimiteEmprestimos();
}
