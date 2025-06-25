package controllers;

import models.Emprestimo;
import models.Livro;
import models.Pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.UsuarioNaoCadastradoException;
import exceptions.NenhumLivroEncontradoException;
import exceptions.LivroIndisponivelException;
import exceptions.LimiteEmprestimosException;
import exceptions.NenhumEmprestimoEncontradoException;

public class Biblioteca {
    private List<Livro> livros;
    private List<Pessoa> usuarios;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarUsuario(Pessoa usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario cadastrado: " + usuario.getNome());
    }

    public boolean removerUsuario(String cpf) {
        return usuarios.removeIf(u -> u.getCpf().equals(cpf));
    }

    public Pessoa buscarUsuarioPorCpf(String cpf) throws UsuarioNaoCadastradoException {
        for (Pessoa u : usuarios) {
            if (u.getCpf().equals(cpf))
                return u;
        }
        try {
            throw new UsuarioNaoCadastradoException("Usuário com CPF - " + cpf + " - não encontrado.");
        }
        catch (UsuarioNaoCadastradoException e) {
            System.out.println(e.getMessage());
            return null;
        }  
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro cadastrado: " + livro.getTitulo());
    }

    public boolean removerLivro(String titulo) {
        return livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    public List<Pessoa> listarUsuarios() {
        return new ArrayList<>(usuarios); // retorna uma cópia dos usuários
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros); // retorna uma cópia dos livros
    }

    public List<Livro> pesquisarPorTitulo(String trecho) throws NenhumLivroEncontradoException {
        List<Livro> encontrados = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getTitulo().toLowerCase().contains(trecho.toLowerCase())) {
                encontrados.add(l);
            }
        }
        if (encontrados.isEmpty()) {
            try {
                throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o trecho: " + trecho);
            }
            catch (NenhumLivroEncontradoException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return encontrados;
    }

    public Livro buscarLivroExato(String titulo) throws NenhumLivroEncontradoException {
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        try {
            throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o titulo: " + titulo);
        }
        catch (NenhumLivroEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Para realizar o emprestimo, precisa do nome do livro e cpf do usuario, isso evita que emprestimos sejam feitos para livros ou usuarios nao cadastrados ou removidos
    public void realizarEmprestimo(String tituloLivro, String cpfUsuario) throws UsuarioNaoCadastradoException, NenhumLivroEncontradoException, LivroIndisponivelException, LimiteEmprestimosException {
        Livro livro = buscarLivroExato(tituloLivro);
        Pessoa usuario = buscarUsuarioPorCpf(cpfUsuario);

        if (livro == null) { 
            try {
                throw new NenhumLivroEncontradoException("Nenhum livro encontrado com o titulo: " + tituloLivro);
            }
            catch (NenhumLivroEncontradoException e) {
                // mensagem ja impressa em buscarLivroExato
                return;
            }
        }

        if (usuario == null) { 
            try{
                throw new UsuarioNaoCadastradoException("Usuário com CPF - " + cpfUsuario + " - não encontrado.");
            }
            catch (UsuarioNaoCadastradoException e) {
                // mensagem ja impressa em buscarUsuarioPorCpf
                return;
            }
        }

        if (!livro.isDisponivel()) {
            try {
                throw new LivroIndisponivelException("Livro - " + tituloLivro + " - não está disponível.");
            }
            catch (LivroIndisponivelException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        if (usuario.getHistoricoEmprestimos().size() >= usuario.getLimiteEmprestimos()) {
            try {
                throw new LimiteEmprestimosException("Usuário - " + usuario.getNome() + " - atingiu o limite de empréstimos.");
            }
            catch (LimiteEmprestimosException e) {
                System.out.println(e.getMessage());
                return;
            }
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
    public void registrarDevolucao(String tituloLivro, LocalDate dataDevolucao) throws NenhumEmprestimoEncontradoException {
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
        try {
            throw new NenhumEmprestimoEncontradoException("Nenhum empréstimo encontrado para o livro: " + tituloLivro);
        }
        catch (NenhumEmprestimoEncontradoException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}