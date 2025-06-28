package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo implements Multavel {
    private Livro livro;
    private Pessoa usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;

    public Emprestimo(Livro livro, Pessoa usuario, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
    }

    public Livro getLivro(){return livro;}
    public Pessoa getUsuario(){return usuario;}
    public LocalDate getDataEmprestimo(){return dataEmprestimo;}
    public LocalDate getDataPrevista(){return dataPrevista;}
    public boolean isAtrasado(LocalDate dataDevolucaoReal){return dataDevolucaoReal.isAfter(dataPrevista);}

    public double calcularMulta(LocalDate dataDevolucaoReal) {
        if (isAtrasado(dataDevolucaoReal)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucaoReal);
            return diasAtraso * 2.50; // R$2,50 por dia de atraso
        }
        return 0.0;
    }

    public String toString() {
        return "Livro: " + livro.getTitulo() + ", Usuário: " + usuario.getNome() +
                ", Empréstimo: " + dataEmprestimo + ", Prevista: " + dataPrevista;
    }
}