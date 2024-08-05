package soma.haeya.edupi_user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import soma.haeya.edupi_user.domain.Role;

@Data
@Builder
@AllArgsConstructor
public class TokenInfo {

    private String email;
    private String name;
    private Role role;
}
