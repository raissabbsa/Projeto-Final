package models;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = true; // Por padrão, um livro recém-cadastrado está disponível
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("O livro já está emprestado.");
        }
        this.disponivel = false;
    }

    public void devolver() {
        if (disponivel) {
            throw new IllegalStateException("O livro já está disponível.");
        }
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "\"" + titulo + "\" por " + autor + " (" + anoPublicacao + ") - " +
                (disponivel ? "Disponível" : "Emprestado");
    }
}
