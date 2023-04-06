package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.binding.UpdateProfileBindingModel;
import bg.softuni.myrealestateproject.model.service.CurrentUser;
import bg.softuni.myrealestateproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
       modelAndView.addObject("userProfile", this.userService.getUserProfileInformation(principal.getName()));
       modelAndView.setViewName("/user/info");

       return modelAndView;
    }

    @GetMapping("/update")
    public String getProfileToUpdate(Model model, Principal principal) {
        if (!model.containsAttribute("updateProfileBindingModel")) {
            UpdateProfileBindingModel updateProfileBindingModel = this.userService.findUpdateProfileBindingModelByEmail(principal.getName());
            model.addAttribute("updateProfileBindingModel", updateProfileBindingModel);
            model.addAttribute("emailExists", false);
            model.addAttribute("phoneNumberExists", false);
        }

        return "/user/update";
    }

    @PutMapping("/update")
    public String confirmUpdate(@Valid UpdateProfileBindingModel updateProfileBindingModel, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, @AuthenticationPrincipal CurrentUser userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateProfileBindingModel", bindingResult);
        }

        boolean existEmail = this.userService.existUserByEmail(updateProfileBindingModel.getEmail(), userDetails.getUsername());
        if (existEmail) {
            redirectAttributes.addFlashAttribute("emailExists", true);
        }

        boolean existPhone = this.userService.existUserByPhoneNumber(updateProfileBindingModel.getPhoneNumber(), userDetails.getPhoneNumber());
        if (existPhone) {
            redirectAttributes.addFlashAttribute("phoneNumberExists", true);
        }

        if (bindingResult.hasErrors() || existEmail || existPhone) {
            redirectAttributes.addFlashAttribute("updateProfileBindingModel", updateProfileBindingModel);
            return "redirect:/profile/update";
        }

        this.userService.updateProfile(updateProfileBindingModel, userDetails.getId());

        return "redirect:/profile";
    }

    @GetMapping("/offers")
    public ModelAndView profileOffers(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("userOffers", this.userService.getUserProfileOffers(principal.getName()));
        modelAndView.setViewName("user/offers");

        return modelAndView;
    }
}
