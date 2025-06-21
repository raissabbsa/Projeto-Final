package models;

public class Aluno extends Pessoa {
    private String matricula;

    public Aluno(String nome, String cpf, String email, String matricula) {
        super(nome, cpf, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return super.toString() + ", Matrícula: " + matricula + " (Aluno)";
    }

    @Override
    public int getLimiteEmprestimos() {
        return 3;
    }
}