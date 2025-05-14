package Squad5.API_FSPH.exception;

// Exceção para regras de negócio violadas
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
