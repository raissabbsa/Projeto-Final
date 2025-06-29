package controllers;

import exceptions.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Aluno;
import models.Departamento;
import models.Emprestimo;
import models.Livro;
import models.Pessoa;
import models.Professor;

public class Biblioteca {
    private List<Livro> livros;
    private List<Pessoa> usuarios;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
    }

    public boolean removerLivro(String titulo) {
        return livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

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

    public void cadastrarUsuario(Pessoa livro) {
        usuarios.add(livro);
    }

    public void removerUsuario(String cpf) throws UsuarioNaoCadastradoException {
        Pessoa usuario = buscarUsuarioExato(cpf);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException("Usuário não encontrado.");
        }
        if (!usuario.getHistoricoEmprestimos().isEmpty()) {
            System.out.println("Não é possível remover o usuário, pois ele possui empréstimos ativos.");
            return;
        }
        usuarios.remove(usuario);
    }

    public List<Pessoa> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public Pessoa buscarUsuarioExato(String cpf) throws UsuarioNaoCadastradoException {
        for (Pessoa u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        throw new UsuarioNaoCadastradoException("Usuário com CPF - " + cpf + " - não encontrado.");

    }

    public List<Emprestimo> listarEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    public void realizarEmprestimo(String tituloLivro, String cpfUsuario) throws UsuarioNaoCadastradoException,
            NenhumLivroEncontradoException, LivroIndisponivelException, LimiteEmprestimosException {
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
            throw new LimiteEmprestimosException(
                    "Usuário - " + usuario.getNome() + " - atingiu o limite de empréstimos.");
        }

        livro.emprestar();
        LocalDate hoje = LocalDate.now();
        LocalDate prevista = hoje.plusDays(14); // Prazo padrão

        Emprestimo emp = new Emprestimo(livro, usuario, hoje, prevista);
        emprestimos.add(emp);
        usuario.adicionarEmprestimo(emp);
    }

    // Registrar devolução
    public void registrarDevolucao(String tituloLivro, LocalDate dataDevolucao)
            throws NenhumEmprestimoEncontradoException {
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

    public void salvarUsuariosEmArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Pessoa u : usuarios) {
                if (u instanceof Aluno) {
                    bw.write("ALUNO," + u.getNome() + "," + u.getCpf() + "," + u.getEmail() + ","
                            + ((Aluno) u).getMatricula());
                } else if (u instanceof Professor) {
                    bw.write("PROFESSOR," + u.getNome() + "," + u.getCpf() + "," + u.getEmail() + ","
                            + ((Professor) u).getDepartamento());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public void carregarUsuariosDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String tipo = partes[0];
                String nome = partes[1];
                String cpf = partes[2];
                String email = partes[3];
                String extra = partes[4];

                if (tipo.equals("ALUNO")) {
                    Aluno a = new Aluno(nome, cpf, email, extra);
                    usuarios.add(a);
                } else if (tipo.equals("PROFESSOR")) {
                    Departamento departamento = Departamento.valueOf(extra); // converte String em Enum
                    Professor p = new Professor(nome, cpf, email, departamento);
                    usuarios.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public void salvarLivrosEmArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("livros.txt"))) {
            for (Livro l : livros) {
                bw.write(l.getTitulo() + "," + l.getAutor() + "," + l.getAnoPublicacao() + "," + l.isDisponivel());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    public void carregarLivrosDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("livros.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String titulo = partes[0];
                String autor = partes[1];
                int ano = Integer.parseInt(partes[2]);
                boolean disponivel = Boolean.parseBoolean(partes[3]);

                Livro livro = new Livro(titulo, autor, ano);
                if (!disponivel) {
                    livro.emprestar();
                }
                livros.add(livro);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar livros: " + e.getMessage());
        }
    }

    public void salvarEmprestimosEmArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("emprestimos.txt"))) {
            for (Emprestimo e : emprestimos) {
                bw.write(
                        e.getLivro().getTitulo() + "," +
                                e.getUsuario().getCpf() + "," +
                                e.getDataEmprestimo() + "," +
                                e.getDataPrevista());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar empréstimos: " + e.getMessage());
        }
    }

    public void carregarEmprestimosDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("emprestimos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String tituloLivro = partes[0];
                String cpfUsuario = partes[1];
                LocalDate dataEmprestimo = LocalDate.parse(partes[2]);
                LocalDate dataPrevista = LocalDate.parse(partes[3]);

                Livro livro = buscarLivroExato(tituloLivro);
                Pessoa usuario = buscarUsuarioExato(cpfUsuario);
                Emprestimo emp = new Emprestimo(livro, usuario, dataEmprestimo, dataPrevista);
                emprestimos.add(emp);
                usuario.adicionarEmprestimo(emp);
            }
        } catch (IOException | UsuarioNaoCadastradoException | NenhumLivroEncontradoException e) {
            System.err.println("Erro ao carregar empréstimos: " + e.getMessage());
        }
    }

    public void editarUsuario(String cpf, String novoNome, String novoEmail, String novoExtra)
            throws UsuarioNaoCadastradoException {
        Pessoa u = buscarUsuarioExato(cpf);
        if (u == null) {
            throw new UsuarioNaoCadastradoException("Usuário não encontrado para edição.");
        }
        u.setNome(novoNome);
        u.setEmail(novoEmail);

        if (u instanceof Aluno) {
            ((Aluno) u).setMatricula(novoExtra);
        } else if (u instanceof Professor) {
            Departamento novoDepto = Departamento.valueOf(novoExtra);
            ((Professor) u).setDepartamento(novoDepto);
        }
    }
}