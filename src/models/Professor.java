package src.models;

public class Professor extends Pessoa {
    private String departamento;

    public Professor(String nome, String cpf, String email, String departamento) {
        super(nome, cpf, email);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return super.toString() + ", Departamento: " + departamento + " (Professor)";
    }
}