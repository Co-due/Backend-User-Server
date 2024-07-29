package soma.haeya.edupi_user.signup.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
