package Squad5.API_FSPH.exception;

// Exceção para operações inválidas em amostras
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
