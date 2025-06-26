package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Emprestimo;
import models.Livro;
import models.Pessoa;

public class Biblioteca {
    private List<Livro> livros;
    private List<Pessoa> usuarios;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios=new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro){livros.add(livro);}
    public boolean removerLivro(String titulo){return livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));}
    public List<Livro> listarLivros(){return new ArrayList<>(livros);}
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

    public void cadastrarUsuario(Pessoa livro){usuarios.add(livro);}
    public boolean removerUsuario(String titulo) {return usuarios.removeIf(l -> l.getNome().equalsIgnoreCase(titulo));}
    public List<Pessoa> listarUsuarios() {return new ArrayList<>(usuarios);}
    public List<Pessoa> pesquisarPorNome(String trecho) {
        List<Pessoa> encontrados = new ArrayList<>();
        for (Pessoa l : usuarios) {
            if (l.getNome().toLowerCase().contains(trecho.toLowerCase())) {
                encontrados.add(l);
            }
        }
        return encontrados;
    }
    public Pessoa buscarUsuarioExato(String titulo) {
        for (Pessoa l : usuarios) {
            if (l.getNome().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        return null; // ou lançar exceção personalizada
    }

    public List<Emprestimo> listarEmprestimos() {return new ArrayList<>(emprestimos);}
    public int realizarEmprestimo(String tituloLivro, String user){
        Pessoa usuario=buscarUsuarioExato(user);
        Livro livro = buscarLivroExato(tituloLivro);
        if (livro == null) {
            return 0;
        }
        if (usuario == null) {
            return -2;
        }

        if (!livro.isDisponivel()) {
            return -1;
        }

        if (usuario.getHistoricoEmprestimos().size() >= usuario.getLimiteEmprestimos()) {
            return -3;
        }

        livro.emprestar();
        LocalDate hoje = LocalDate.now();
        LocalDate prevista = hoje.plusDays(14); // Prazo padrão

        Emprestimo emp = new Emprestimo(livro, usuario, hoje, prevista);
        emprestimos.add(emp);
        usuario.adicionarEmprestimo(emp);
        return 1;
    }

    // Registrar devolução
    public String registrarDevolucao(Emprestimo emprestimo, LocalDate dataDevolucao) {
        emprestimo.getLivro().devolver();
        double multa = emprestimo.calcularMulta(dataDevolucao);
        emprestimo.getUsuario().removerEmprestimo(emprestimo);
        emprestimos.remove(emprestimo);
        return ("Devolução realizada. Multa: R$ " + String.format("%.2f", multa));
    }
}