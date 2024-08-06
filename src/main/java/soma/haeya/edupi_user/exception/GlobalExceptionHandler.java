package soma.haeya.edupi_user.exception;

import io.jsonwebtoken.JwtException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soma.haeya.edupi_user.dto.response.Response;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)    // @Valid 실패에 대한 예외 처리
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 오류 목록을 반복
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DbValidException.class)    // DB 저장 실패에 대한 예외 처리
    public ResponseEntity<Response> handleValidationExceptions(DbValidException ex) {
        Response errors = new Response(ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnexpectedServerException.class)    // 예상치 못한 예외에 대한 처리
    public ResponseEntity<Response> handleValidationExceptions(UnexpectedServerException ex) {
        log.error("Unexpected server error occurred: ", ex);
        Response error = new Response("Internal server error");

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JwtException.class)    // Jwt 기한 만료 예외 처리
    public ResponseEntity<Response> handleValidationExceptions(JwtException ex) {
        log.error("Unexpected server error occurred: ", ex);
        Response error = new Response("토큰이 유효하지 않습니다.");

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<Response> handleMissingCookieException(MissingRequestCookieException ex) {
        Response error = new Response("토큰이 없습니다.");

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(error);
    }
}
