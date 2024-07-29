package soma.haeya.edupi_user.signup.exception;

public class DbValidException extends RuntimeException{
    private int errorCode;

    public DbValidException(String message) {
        super(message);
    }

    public DbValidException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
