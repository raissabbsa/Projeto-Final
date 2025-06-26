package controllers;

import exceptions.*;
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
    public List<Livro> pesquisarPorTitulo(String trecho) throws NenhumLivroEncontradoException {
        List<Livro> encontrados = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getTitulo().toLowerCase().contains(trecho.toLowerCase())) {
                encontrados.add(l);
            }
        }
        if (encontrados.isEmpty()) {
            throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o trecho: " + trecho);
        }
        return encontrados;
    }
    public Livro buscarLivroExato(String titulo) throws NenhumLivroEncontradoException {
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o titulo: " + titulo);
    }

    public void cadastrarUsuario(Pessoa livro){usuarios.add(livro);}
    public boolean removerUsuario(String titulo) {return usuarios.removeIf(l -> l.getNome().equalsIgnoreCase(titulo));}
    public List<Pessoa> listarUsuarios() {return new ArrayList<>(usuarios);}
    public Pessoa buscarUsuarioExato(String cpf) throws UsuarioNaoCadastradoException {
        for (Pessoa u : usuarios) {
            if (u.getCpf().equals(cpf)){
                return u;}
        }
        throw new UsuarioNaoCadastradoException("Usuário com CPF - " + cpf + " - não encontrado.");
        
    }

    public List<Emprestimo> listarEmprestimos() {return new ArrayList<>(emprestimos);}
    public void realizarEmprestimo(String tituloLivro, String cpfUsuario) throws UsuarioNaoCadastradoException, NenhumLivroEncontradoException, LivroIndisponivelException, LimiteEmprestimosException {
        Livro livro = buscarLivroExato(tituloLivro);
        Pessoa usuario = buscarUsuarioExato(cpfUsuario);

        if (livro == null) { 
            throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o titulo: " + tituloLivro);
        }

        if (usuario == null) {
                throw new UsuarioNaoCadastradoException("Usuário com CPF - " + cpfUsuario + " - não encontrado.");
        }

        if (!livro.isDisponivel()) {
            throw new LivroIndisponivelException("Livro - " + tituloLivro + " - não está disponível.");
        }

        if (usuario.getHistoricoEmprestimos().size() >= usuario.getLimiteEmprestimos()) {
            throw new LimiteEmprestimosException("Usuário - " + usuario.getNome() + " - atingiu o limite de empréstimos.");
        }

        livro.emprestar();
        LocalDate hoje = LocalDate.now();
        LocalDate prevista = hoje.plusDays(14); // Prazo padrão

        Emprestimo emp = new Emprestimo(livro, usuario, hoje, prevista);
        emprestimos.add(emp);
        usuario.adicionarEmprestimo(emp);
    }

    // Registrar devolução
    public void registrarDevolucao(String tituloLivro, LocalDate dataDevolucao) throws NenhumEmprestimoEncontradoException {
        for (Emprestimo emp : emprestimos) {
            if (emp.getLivro().getTitulo().equalsIgnoreCase(tituloLivro)) {
                emp.getLivro().devolver();
                double multa = emp.calcularMulta(dataDevolucao);
                emp.getUsuario().removerEmprestimo(emp);
                emprestimos.remove(emp);
                return;
            }
        }
        throw new NenhumEmprestimoEncontradoException("Nenhum empréstimo encontrado para o livro: " + tituloLivro);
    }
}