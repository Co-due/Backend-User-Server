package soma.haeya.edupi_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import soma.haeya.edupi_user.dto.request.MemberLoginRequest;
import soma.haeya.edupi_user.dto.TokenInfo;
import soma.haeya.edupi_user.dto.request.SignupRequest;
import soma.haeya.edupi_user.dto.response.ErrorResponse;
import soma.haeya.edupi_user.service.MemberService;
import soma.haeya.edupi_user.exception.DbValidException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest,
        HttpServletResponse response) {
        String token = memberService.login(memberLoginRequest);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/info")
    public ResponseEntity<TokenInfo> loginInfo(@CookieValue("token") String token) {
        TokenInfo tokenInfo = memberService.findMemberInfo(token);

        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> createPost(@Valid @RequestBody SignupRequest signupRequest) {
        // DB에 저장하기
        RestClient restClient = RestClient.create();

        return restClient.post()
                .uri("http://localhost:8081/api/v1/user/save/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(signupRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    ErrorResponse errorResponse =  new ObjectMapper().readValue (res.getBody(), ErrorResponse.class);
                    throw new DbValidException(errorResponse.message());
                })
                .toBodilessEntity();
    }
}
