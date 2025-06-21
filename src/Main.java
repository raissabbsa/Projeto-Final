import java.time.LocalDate;

import controllers.Biblioteca;
import models.Aluno;
import models.Livro;
import models.Professor;

public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno("Carlos", "123.456.789-00", "carlos@email.com", "RA202500");
        Professor professor = new Professor("Dra. Ana", "987.654.321-00", "ana@universidade.edu", "Engenharia");

        Biblioteca biblioteca = new Biblioteca();

        Livro l1 = new Livro("1984", "George Orwell", 1949);
        Livro l2 = new Livro("Dom Casmurro", "Machado de Assis", 1899);
        Livro l3 = new Livro("O Alienista", "Machado de Assis", 1882);
        Livro l4 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro l5 = new Livro("Harry Potter e a Pedra Filosofal", "J. K. Rowling", 2018);

        biblioteca.cadastrarLivro(l1);
        biblioteca.cadastrarLivro(l2);
        biblioteca.cadastrarLivro(l3);
        biblioteca.cadastrarLivro(l4);
        biblioteca.cadastrarLivro(l5);

        // Cadastrar e emprestar 3 livros
        for (int i = 1; i <= 4; i++) {
            Livro livro = new Livro("Livro " + i, "Autor", 2023);
            biblioteca.cadastrarLivro(livro);
            biblioteca.realizarEmprestimo("Livro " + i, aluno);
        }

        aluno.exibirHistorico();
    }
}