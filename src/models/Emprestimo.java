package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo implements Multavel {
    public static int totalEmprestimosRealizados = 0;
    
    private Livro livro;
    private Pessoa usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private boolean ativo = true; 

    public Emprestimo(Livro livro, Pessoa usuario, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.ativo = true; // Define o empréstimo como ativo ao ser criado
        totalEmprestimosRealizados++;
    }

    public Livro getLivro(){return livro;}
    public Pessoa getUsuario(){return usuario;}
    public LocalDate getDataEmprestimo(){return dataEmprestimo;}
    public LocalDate getDataPrevista(){return dataPrevista;}
    public boolean isAtrasado(LocalDate dataDevolucaoReal){return dataDevolucaoReal.isAfter(dataPrevista);}
    public static int getTotalEmprestimosRealizados() {
        return totalEmprestimosRealizados;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void finalizarEmprestimo() {
        this.ativo = false;
    }
    public double calcularMulta(LocalDate dataDevolucaoReal) {
        if (isAtrasado(dataDevolucaoReal)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucaoReal);
            return diasAtraso * 2.50; // R$2,50 por dia de atraso
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Livro: " + livro.getTitulo() + ", Usuário: " + usuario.getNome() +
                ", Empréstimo: " + dataEmprestimo + ", Prevista: " + dataPrevista;
    }
}