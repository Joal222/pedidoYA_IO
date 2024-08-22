package FormatoBase.proyectoJWT.exception;

public class PasaporteExistenteException extends RuntimeException {

    public PasaporteExistenteException() {
        super();
    }

    public PasaporteExistenteException(String message) {
        super(message);
    }

    public PasaporteExistenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasaporteExistenteException(Throwable cause) {
        super(cause);
    }
}
