package soma.haeya.edupi_user.login.service;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soma.haeya.edupi_user.login.auth.TokenProvider;
import soma.haeya.edupi_user.login.client.MemberApiClient;
import soma.haeya.edupi_user.login.domain.Member;
import soma.haeya.edupi_user.login.domain.Role;
import soma.haeya.edupi_user.login.dto.MemberLoginRequest;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberApiClient memberRepository;

    @Mock
    private TokenProvider tokenProvider;

    private MemberLoginRequest memberLoginRequest;

    @BeforeEach
    void init() {
        memberLoginRequest = MemberLoginRequest.builder()
            .email("asdf@naver.com")
            .password("asdf1234")
            .build();
    }


    @Test
    @DisplayName("아이디와 패스워드에 맞는 멤버가 있으면 token을 반환한다.")
    void memberLogin() {

        Member expectedMember = new Member("asdf@naver.com", "홍길동", Role.ROLE_USER);

        String expectedToken = "token";

        when(memberRepository.retrieveMemberByEmailAndPassword(memberLoginRequest)).thenReturn(
            expectedMember);
        when(tokenProvider.generateToken(expectedMember)).thenReturn(expectedToken);

        String resultToken = memberService.login(memberLoginRequest);

        Assertions.assertThat(resultToken).isEqualTo(expectedToken);
    }

    @Test
    @DisplayName("아이디 패스워드에 맞는 멤버가 없으면 예외를 반환한다.")
    void memberLoginException() {

        when(memberRepository.retrieveMemberByEmailAndPassword(memberLoginRequest)).thenThrow(
            new IllegalArgumentException("아이디 비밀번호가 일치하지 않습니다.")
        );

        Assertions.assertThatThrownBy(() -> memberService.login(memberLoginRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("아이디 비밀번호가 일치하지 않습니다.");
    }

}
