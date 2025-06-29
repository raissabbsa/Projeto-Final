package models;

public enum Departamento {
    ENGENHARIA_DA_COMPUTACAO,
    ENGENHARIA_ELETRICA,
    ENGENHARIA_MECANICA,
    MEDICINA,
    MATEMATICA,
    LETRAS,
    HISTORIA,
    FISICA,
    QUIMICA,
    ARTES;

    public String getNomeFormatado(){
        String nomeFormatado = this.name().toLowerCase().replace('_', ' ');
        return nomeFormatado.substring(0, 1).toUpperCase() + nomeFormatado.substring(1);
    }
}
