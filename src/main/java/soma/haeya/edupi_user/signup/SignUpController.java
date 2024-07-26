package soma.haeya.edupi_user.signup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import soma.haeya.edupi_user.signup.dto.SignUpDTO;

@Slf4j
@RestController
@RequestMapping("/user/*")
public class SignUpController {

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String createPost(@RequestBody SignUpDTO signUpDTO) {
        ModelAndView mav = new ModelAndView();

        log.info("[SignUpDTO] {}", signUpDTO);
        return "ok";
    }
}
