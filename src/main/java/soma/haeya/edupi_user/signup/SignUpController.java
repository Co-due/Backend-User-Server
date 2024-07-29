package soma.haeya.edupi_user.signup;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;
import soma.haeya.edupi_user.signup.dto.SignUpDTO;

@Slf4j
@RestController
@RequestMapping("/user/*")
public class SignUpController {

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity<Void> createPost(@Valid @RequestBody SignUpDTO signUpDTO) {
        log.info("[SignUpDTO] {}", signUpDTO);

        // DB에 저장하기
        RestClient restClient = RestClient.create();

        ResponseEntity<Void> response = restClient.post()
                .uri("http://localhost:8081/api/v1/save/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(signUpDTO)
                .retrieve()
                .toBodilessEntity();

        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
