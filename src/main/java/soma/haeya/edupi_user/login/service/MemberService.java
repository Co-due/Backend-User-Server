package soma.haeya.edupi_user.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soma.haeya.edupi_user.login.auth.TokenProvider;
import soma.haeya.edupi_user.login.client.MemberApiClient;
import soma.haeya.edupi_user.login.domain.Member;
import soma.haeya.edupi_user.login.dto.MemberLoginRequest;
import soma.haeya.edupi_user.login.dto.TokenInfo;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberApiClient memberApiClient;
    private final TokenProvider tokenProvider;

    public String login(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberApiClient.findMemberByEmailAndPassword(memberLoginRequest);

        return tokenProvider.generateToken(findMember);
    }

    public TokenInfo findMemberInfo(String token) {
        return tokenProvider.findUserInfoBy(token);
    }


}
