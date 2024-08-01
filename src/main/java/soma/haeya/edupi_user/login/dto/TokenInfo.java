package soma.haeya.edupi_user.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import soma.haeya.edupi_user.login.domain.Role;

@Data
@Builder
@AllArgsConstructor
public class TokenInfo {

    private String email;
    private String name;
    private Role role;
}
