package soma.haeya.edupi_user.signup;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import soma.haeya.edupi_user.signup.dto.ErrorResponse;
import soma.haeya.edupi_user.signup.dto.SignUpDTO;
import soma.haeya.edupi_user.signup.exception.DbValidException;


@Slf4j
@RestController
@RequestMapping("/user/*")
public class SignUpController {

    @PostMapping(value = "signup")
    public ResponseEntity<Void> createPost(@Valid @RequestBody SignUpDTO signUpDTO) {
        // DB에 저장하기
        RestClient restClient = RestClient.create();

        return restClient.post()
                .uri("http://localhost:8081/api/v1/user/save/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(signUpDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    ErrorResponse errorResponse =  new ObjectMapper().readValue (res.getBody(), ErrorResponse.class);
                    throw new DbValidException(errorResponse.message(), HttpStatus.BAD_REQUEST.value());
                })
                .toBodilessEntity();
    }
}
