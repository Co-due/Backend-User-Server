package soma.haeya.edupi_user.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginRequest {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

}
