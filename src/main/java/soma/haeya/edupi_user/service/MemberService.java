package soma.haeya.edupi_user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soma.haeya.edupi_user.auth.TokenProvider;
import soma.haeya.edupi_user.client.MemberApiClient;
import soma.haeya.edupi_user.domain.Member;
import soma.haeya.edupi_user.dto.request.MemberLoginRequest;
import soma.haeya.edupi_user.dto.TokenInfo;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberApiClient memberApiClient;
    private final TokenProvider tokenProvider;

    public String login(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberApiClient.retrieveMemberByEmailAndPassword(memberLoginRequest);

        return tokenProvider.generateToken(findMember);
    }

    public TokenInfo findMemberInfo(String token) {
        return tokenProvider.findUserInfoBy(token);
    }


}
