package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.binding.UserRegisterBindingModel;
import bg.softuni.myrealestateproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegisterController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    public RegisterController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/register")
    private String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
            model.addAttribute("emailExists", false);
            model.addAttribute("phoneNumberExists", false);
            model.addAttribute("passwordsNotMatched", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);

        }

        boolean passwordsNotMatched = this.userService.passwordsMatcher(userRegisterBindingModel);
        if (passwordsNotMatched) {
            redirectAttributes.addFlashAttribute("passwordsNotMatched", true);
        }

        boolean existEmail = this.userService.existByEmail(userRegisterBindingModel.getEmail());
        if (existEmail) {
            redirectAttributes.addFlashAttribute("emailExists", true);
        }

        boolean existPhoneNumber = this.userService.existByPhoneNumber(userRegisterBindingModel.getPhoneNumber());
        if (existPhoneNumber) {
            redirectAttributes.addFlashAttribute("phoneNumberExists", true);
        }

        if (bindingResult.hasErrors() || existEmail || existPhoneNumber || passwordsNotMatched) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        userService.registerUser(userRegisterBindingModel, successfulAuth -> {
            // populating security context
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);

            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }

}
