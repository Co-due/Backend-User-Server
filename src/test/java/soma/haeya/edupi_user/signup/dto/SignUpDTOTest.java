package soma.haeya.edupi_user.signup.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;


class SignUpDTOTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void 정상_회원가입_형식(){
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .email("rladbwls0000@gmail.com")
                .name("yujin")
                .password("rladbwls000@")
                .phoneNumber("010-1234-5678")
                .build();

        Set<ConstraintViolation<SignUpDTO>> exceptions = validator.validate(signUpDTO);

        Assertions.assertEquals(exceptions.size(), 0);
    }


    @Test
    void 실패_잘못된_이메일_형식(){
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .email("rladbwls0000")
                .name("yujin")
                .password("rladbwls000@")
                .build();

        Set<ConstraintViolation<SignUpDTO>> exceptions = validator.validate(signUpDTO);

        Assertions.assertFalse(exceptions.isEmpty());
        ConstraintViolation<SignUpDTO> violation = exceptions.iterator().next();
        Assertions.assertEquals("잘못된 이메일 형식입니다.", violation.getMessage());
        Assertions.assertEquals("email", violation.getPropertyPath().toString());

    }

    @Test
    void 실패_회원가입_요청_빈값() {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .email("")
                .name("")
                .password("")
                .build();

        Set<ConstraintViolation<SignUpDTO>> exceptions = validator.validate(signUpDTO);

        Assertions.assertFalse(exceptions.isEmpty());

    }
        @Test
        void 실패_회원가입_요청_잘못된_비민번호(){
            SignUpDTO signUpDTO = SignUpDTO.builder()
                    .email("")
                    .name("")
                    .password("12345")
                    .build();

            Set<ConstraintViolation<SignUpDTO>> exceptions = validator.validate(signUpDTO);

            Assertions.assertFalse(exceptions.isEmpty());
    }

}