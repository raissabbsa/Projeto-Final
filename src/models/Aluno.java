package models;

public class Aluno extends Pessoa {
    private String matricula;

    public Aluno(String nome, String cpf, String email, String matricula) {
        super(nome, cpf, email);
        this.matricula = matricula;
        this.tipo = TipoUsuario.ALUNO;
    }
    public Aluno(String pfp,String nome, String cpf, String email, String matricula) {
        super(pfp, nome, cpf, email);
        this.matricula = matricula;
        this.tipo = TipoUsuario.ALUNO;
    }

    public String getMatricula(){return matricula;}
    public void setMatricula(String matricula){this.matricula = matricula;}

    public String toString() {
        return super.toString() + ", Matrícula: " + matricula + " (Aluno)";
    }

    public int getLimiteEmprestimos() {
        return 3;
    }
}