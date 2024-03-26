package th.co.ais.mimo.debt.exception;

public class ExemptException extends Exception {

    private final String code;


    public ExemptException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ExemptException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }




}
