package models;

public class Professor extends Pessoa {
    private Departamento departamento;

    public Professor(String nome, String cpf, String email, Departamento departamento) {
        super(nome, cpf, email);
        this.departamento = departamento;
        this.tipo = TipoUsuario.PROFESSOR;
    }
    public Professor(String nome, String cpf, String email, String departamento) {
        super(nome, cpf, email);
        this.departamento = Departamento.StringToDept(departamento);
        this.tipo = TipoUsuario.PROFESSOR;
    }
    public Professor(String pfp,String nome, String cpf, String email, Departamento departamento) {
        super(pfp,nome, cpf, email);
        this.departamento = departamento;
        this.tipo = TipoUsuario.PROFESSOR;
    }
    public Professor(String pfp,String nome, String cpf, String email, String departamento) {
        super(pfp,nome, cpf, email);
        this.departamento = Departamento.StringToDept(departamento);
        this.tipo = TipoUsuario.PROFESSOR;
    }

    public Departamento getDepartamento(){return departamento;}
    public void setDepartamento(Departamento departamento){this.departamento = departamento;}

    public String toString() {
        return super.toString() + ", Departamento: " + departamento.name() + " (Professor)";
    }

    public int getLimiteEmprestimos() {
        return 5;
    }
}