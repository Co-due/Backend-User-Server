package soma.haeya.edupi_user.login.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {

    private final Long id;
    private final String password;
    private final String email;
    private final String name;
    private final Role role;

    @JsonCreator
    public Member(Long id, String email, String password, String name, String role
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = Role.valueOf(role);
    }
}
