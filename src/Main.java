import controllers.Biblioteca;
import gui.*;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Carregar dados do arquivo
        biblioteca.carregarUsuariosDoArquivo();
        biblioteca.carregarLivrosDoArquivo();
        biblioteca.carregarEmprestimosDoArquivo();

        new GUI(biblioteca);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            biblioteca.salvarUsuariosEmArquivo();
            biblioteca.salvarLivrosEmArquivo();
            biblioteca.salvarEmprestimosEmArquivo();
            System.out.println("Dados salvos com sucesso!");
        }));
    }
}