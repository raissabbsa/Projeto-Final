import java.time.LocalDate;

import controllers.Biblioteca;
import models.*;
import gui.*;

public class Main {
    public static void main(String[] args) {
        Aluno aluno_carlos = new Aluno("Carlos", "123.456.789-00", "carlos@email.com", "RA202500");
        Professor professor_ana = new Professor("Dra. Ana", "987.654.321-00", "ana@universidade.edu", Departamento.ENGENHARIA_DA_COMPUTACAO);

        Biblioteca biblioteca = new Biblioteca();

        Livro l1 = new Livro("1984", "George Orwell", 1949);
        Livro l2 = new Livro("Dom Casmurro", "Machado de Assis", 1899);
        Livro l3 = new Livro("O Alienista", "Machado de Assis", 1882);
        Livro l4 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro l5 = new Livro("Orgulho e Preconceito", "Jane Austen", 1813);
        Livro l6 = new Livro("Pensando em Java", "Brucke Eckel", 1998);
        Livro l7 = new Livro("Hamlet", "William Shakespeare", 1623);
        Livro l8 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943);
        Livro l9 = new Livro("Cálculo: Volume 1", "James Stewart", 2005);


        biblioteca.cadastrarLivro(l1);
        biblioteca.cadastrarLivro(l2);
        biblioteca.cadastrarLivro(l3);
        biblioteca.cadastrarLivro(l4);
        biblioteca.cadastrarLivro(l5);
        biblioteca.cadastrarLivro(l6);
        biblioteca.cadastrarLivro(l7);
        biblioteca.cadastrarLivro(l8);
        biblioteca.cadastrarLivro(l9);

        biblioteca.cadastrarUsuario(aluno_carlos);
        biblioteca.cadastrarUsuario(professor_ana);

        try {
            biblioteca.realizarEmprestimo("Dom Casmurro", aluno_carlos.getCpf());
            biblioteca.realizarEmprestimo("O Senhor dos Anéis", aluno_carlos.getCpf());
            biblioteca.realizarEmprestimo("Cálculo: Volume 1", professor_ana.getCpf());
        }
        catch (exceptions.UsuarioNaoCadastradoException |
                exceptions.NenhumLivroEncontradoException |
                exceptions.LivroIndisponivelException |
                exceptions.LimiteEmprestimosException e) {
        }

        new GUI(biblioteca);
    }
}