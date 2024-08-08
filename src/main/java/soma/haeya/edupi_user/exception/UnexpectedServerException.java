package soma.haeya.edupi_user.exception;

/*
  개발자가 예상치 못한 예외처리
 */
public class UnexpectedServerException extends RuntimeException {

    public UnexpectedServerException(String message) {
        super(message);
    }

    public UnexpectedServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
