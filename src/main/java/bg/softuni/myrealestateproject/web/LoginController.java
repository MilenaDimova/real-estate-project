package bg.softuni.myrealestateproject.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {

    @GetMapping("/signin")
    public String login() {
        return "signin";
    }

    @PostMapping("/signin-error")
    public String onFailedLogin(@ModelAttribute("email") String email, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("email", email);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/signin";
    }
}
