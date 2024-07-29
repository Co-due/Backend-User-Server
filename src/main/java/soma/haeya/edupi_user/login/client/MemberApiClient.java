package soma.haeya.edupi_user.login.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import soma.haeya.edupi_user.login.domain.Member;
import soma.haeya.edupi_user.login.dto.MemberLoginRequest;

@Component
@HttpExchange("/api/v1/member")
public interface MemberApiClient {

    @PostExchange("/login")
    Member findMemberByEmailAndPassword(@RequestBody MemberLoginRequest memberLoginRequest);

}
