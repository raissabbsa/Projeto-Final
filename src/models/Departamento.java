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
    public static Departamento StringToDept(String dept){
        switch(dept.toUpperCase()){
            case("MATEMATICA"):
                return MATEMATICA;
            case("HISTORIA"):
                return HISTORIA;
            case("LETRAS"):
                return LETRAS;
            case("FISICA"):
                return FISICA;
            case("QUIMICA"):
                return  QUIMICA;
            case("ARTES"):
                return ARTES;
            case("MEDICINA"):
                return MEDICINA;
            case("ENGENHARIA DA COMPUTACAO"):
                return ENGENHARIA_DA_COMPUTACAO;
            case("ENGENHARIA ELETRICA"):
                return ENGENHARIA_ELETRICA;
            case("ENGENHARIA MECANICA"):
                return ENGENHARIA_MECANICA;
            case("ENGENHARIA_DA_COMPUTACAO"):
                return ENGENHARIA_DA_COMPUTACAO;
            case("ENGENHARIA_ELETRICA"):
                return ENGENHARIA_ELETRICA;
            case("ENGENHARIA_MECANICA"):
                return ENGENHARIA_MECANICA;
            default:
            return null;
        }
    }
}