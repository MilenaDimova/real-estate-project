package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.binding.UpdateProfileBindingModel;
import bg.softuni.myrealestateproject.model.service.CurrentUser;
import bg.softuni.myrealestateproject.model.view.OwnerViewModel;
import bg.softuni.myrealestateproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
       modelAndView.addObject("userProfile", this.userService.getUserProfileInformation(principal.getName()));
       modelAndView.setViewName("user/info");

       return modelAndView;
    }


    @GetMapping("/update")
    public ModelAndView update(@Valid UpdateProfileBindingModel updateProfileBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, ModelAndView modelAndView, @AuthenticationPrincipal CurrentUser userDetails, Map<String, Object> modelMap) {
        if (updateProfileBindingModel.isHasErrors()) {
            if (updateProfileBindingModel.isExistEmail()) {
                //bindingResult.rejectValue("email", "error.updateProfileBindingModel", "An account already exists for this email.");
                modelAndView.addObject("emailExists", true);
            }

            if (updateProfileBindingModel.isExistPhoneNumber()) {
                modelAndView.addObject("phoneNumberExists", true);
            }

            modelMap.put("updateProfileBindingModel", updateProfileBindingModel);
            modelAndView.addObject("userProfile", updateProfileBindingModel);
        } else {
            OwnerViewModel user =  this.userService.findById(userDetails.getId());
            modelMap.put("updateProfileBindingModel", user);
            modelAndView.addObject("userProfile", user);
        }
        modelAndView.setViewName("user/update");

        return modelAndView;
    }

    @PutMapping("/update")
    public ModelAndView confirmUpdate(@Valid UpdateProfileBindingModel updateProfileBindingModel, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, ModelAndView modelAndView, @AuthenticationPrincipal CurrentUser userDetails) {
        boolean doesEmailExists = false;
        if (!updateProfileBindingModel.getEmail().equals(userDetails.getUsername())) {
            doesEmailExists = this.userService.existUserByEmail(updateProfileBindingModel.getEmail());
        }

        boolean doesPhoneNumberExist = false;
        if (!updateProfileBindingModel.getPhoneNumber().equals(userDetails.getPhoneNumber())) {
            doesPhoneNumberExist = this.userService.existUserByPhoneNumber(updateProfileBindingModel.getPhoneNumber());
        }

        if (bindingResult.hasErrors() || doesEmailExists || doesPhoneNumberExist) {
            if (doesEmailExists) {
                //ObjectError error = new ObjectError("email","An account already exists for this email.");
                //bindingResult.addError(error);
                //bindingResult.rejectValue("email", "", "An account already exists for this email.");
                updateProfileBindingModel.setExistEmail(true);
            }

            if (doesPhoneNumberExist) {
                updateProfileBindingModel.setExistPhoneNumber(true);
            }

            updateProfileBindingModel.setHasErrors(true);
            redirectAttributes.addFlashAttribute("updateProfileBindingModel", updateProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateProfileBindingModel", bindingResult);
            modelAndView.setViewName(String.format("redirect:/profile/update"));
            return modelAndView;
        }

        updateProfileBindingModel.setHasErrors(false);
        this.userService.updateProfile(updateProfileBindingModel, userDetails.getId());
        modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }

    @GetMapping("/offers")
    public ModelAndView profileOffers(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("userOffers", this.userService.getUserProfileOffers(principal.getName()));
        modelAndView.setViewName("user/offers");

        return modelAndView;
    }
}
