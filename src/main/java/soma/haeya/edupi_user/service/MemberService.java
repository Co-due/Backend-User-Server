package soma.haeya.edupi_user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import soma.haeya.edupi_user.auth.TokenProvider;
import soma.haeya.edupi_user.client.MemberApiClient;
import soma.haeya.edupi_user.domain.Member;
import soma.haeya.edupi_user.dto.TokenInfo;
import soma.haeya.edupi_user.dto.request.MemberLoginRequest;
import soma.haeya.edupi_user.dto.request.SignupRequest;
import soma.haeya.edupi_user.dto.response.Response;
import soma.haeya.edupi_user.exception.DbValidException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberApiClient memberApiClient;
  private final TokenProvider tokenProvider;
  private final ObjectMapper objectMapper;

  public String login(MemberLoginRequest memberLoginRequest) {
    Member findMember = memberApiClient.retrieveMemberByEmailAndPassword(memberLoginRequest);

    return tokenProvider.generateToken(findMember);
  }

  public ResponseEntity<Response> signUp(SignupRequest signupRequest) throws JsonProcessingException {
    try {
      return memberApiClient.saveMember(signupRequest);
    } catch (HttpClientErrorException e) {
      Response response = objectMapper.readValue(e.getResponseBodyAsString(), Response.class);

      if (e.getStatusCode().is4xxClientError()) {
        throw new DbValidException(response.message());
      } else if (e.getStatusCode().is5xxServerError()) {
        // TODO: 후에 정의할 에러 핸들링 로직 추가
        throw new DbValidException(response.message());
      }
    }
    return ResponseEntity.internalServerError()
        .body(new Response("unexpected error"));
  }

  public TokenInfo findMemberInfo(String token) {
    return tokenProvider.findUserInfoBy(token);
  }


}
