package soma.haeya.edupi_user.signup.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class SignUpDTO
{
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String name;
    private String phoneNumber;
}
