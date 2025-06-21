import models.Aluno;
import models.Livro;
import models.Professor;

public class Main {
    public static void main(String[] args) {
        Aluno a = new Aluno("Carlos", "123.456.789-00", "carlos@email.com", "RA202500");
        Professor p = new Professor("Dra. Ana", "987.654.321-00", "ana@universidade.edu", "Engenharia");

        System.out.println(a);
        System.out.println(p);

        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        System.out.println(livro);

        livro.emprestar();
        System.out.println("Depois do empréstimo: " + livro);

        livro.devolver();
        System.out.println("Depois da devolução: " + livro);

    }
}