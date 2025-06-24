package controllers;

import models.Emprestimo;
import models.Livro;
import models.Pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        this.livros = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro cadastrado: " + livro.getTitulo());
    }

    public boolean removerLivro(String titulo) {
        return livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros); // retorna uma cópia
    }

    public List<Livro> pesquisarPorTitulo(String trecho) {
        List<Livro> encontrados = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getTitulo().toLowerCase().contains(trecho.toLowerCase())) {
                encontrados.add(l);
            }
        }
        return encontrados;
    }

    public Livro buscarLivroExato(String titulo) {
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        return null; // ou lançar exceção personalizada
    }

    public void realizarEmprestimo(String tituloLivro, Pessoa usuario) {
        Livro livro = buscarLivroExato(tituloLivro);
        if (livro == null) {
            System.out.println("Livro não encontrado."); // adicionar exceção personalizada
            return;
        }

        if (!livro.isDisponivel()) {
            System.out.println("Livro indisponível."); // adicionar exceção personalizada
            return;
        }

        if (usuario.getHistoricoEmprestimos().size() >= usuario.getLimiteEmprestimos()) {
            System.out.println("Usuário atingiu o limite de empréstimos."); // adicionar exceção personalizada
            return;
        }

        livro.emprestar();
        LocalDate hoje = LocalDate.now();
        LocalDate prevista = hoje.plusDays(14); // Prazo padrão

        Emprestimo emp = new Emprestimo(livro, usuario, hoje, prevista);
        emprestimos.add(emp);
        usuario.adicionarEmprestimo(emp);

        System.out.println("Empréstimo registrado com sucesso.");
    }

    // Registrar devolução
    public void registrarDevolucao(String tituloLivro, LocalDate dataDevolucao) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getLivro().getTitulo().equalsIgnoreCase(tituloLivro)) {
                emp.getLivro().devolver();
                double multa = emp.calcularMulta(dataDevolucao);
                System.out.println("Devolução realizada. Multa: R$ " + String.format("%.2f", multa));
                emp.getUsuario().removerEmprestimo(emp);
                emprestimos.remove(emp);
                return;
            }
        }
        System.out.println("Empréstimo não encontrado.");
    }
}