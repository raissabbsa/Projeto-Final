package exceptions;

public class UsuarioNaoCadastradoException extends Exception {
    public UsuarioNaoCadastradoException(String mensagem) {
        super(mensagem);
    }
}
