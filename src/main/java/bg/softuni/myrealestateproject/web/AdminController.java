package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.binding.AdminUpdateProfileBindingModel;
import bg.softuni.myrealestateproject.service.ImageService;
import bg.softuni.myrealestateproject.service.OfferService;
import bg.softuni.myrealestateproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OfferService offerService;
    private final UserService userService;
    private final ImageService imageService;

    public AdminController(OfferService offerService, UserService userService, ImageService imageService) {
        this.offerService = offerService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/offers")
    public ModelAndView adminOffers(ModelAndView modelAndView, @PageableDefault(size = 7) Pageable pageable) {
        modelAndView.addObject("offers", this.offerService.findAllOffers(pageable));
        modelAndView.addObject("controllerAction", "");
        modelAndView.setViewName("admin/offers");

        return modelAndView;
    }

    @PreAuthorize("@offerService.isAdmin(#principal.name)")
    @DeleteMapping("/delete/")
    public String delete(Principal principal, @RequestParam("id") Long id) {
        this.imageService.deleteAllImagesByOfferId(id);
        this.offerService.deleteOfferById(id);

        return "redirect:/admin/offers";
    }

    @GetMapping("/users")
    public ModelAndView adminUsers(ModelAndView modelAndView, @PageableDefault(size = 7) Pageable pageable) {
        modelAndView.addObject("users", this.userService.findAllUsers(pageable));
        modelAndView.addObject("controllerAction", "");
        modelAndView.setViewName("admin/users");

        return modelAndView;
    }

    @PreAuthorize("@offerService.isAdmin(#principal.name)")
    @GetMapping("/user/")
    public String update(@RequestParam("id") Long id, Model model, Principal principal) {
        if (!model.containsAttribute("adminUpdateProfileBindingModel")) {
            model.addAttribute("adminUpdateProfileBindingModel", this.userService.findUserById(id));
        }

        return "admin/user-profile";
    }


    @GetMapping("/user/update/")
    public String update(@RequestParam("id") Long id, Model model) {
        if (!model.containsAttribute("adminUpdateProfileBindingModel")) {
            model.addAttribute("adminUpdateProfileBindingModel", this.userService.findUserById(id));
            model.addAttribute("emailExists", false);
            model.addAttribute("phoneNumberExists", false);
        }

        return "admin/user-update";
    }

    @PreAuthorize("@offerService.isAdmin(#principal.name)")
    @PutMapping("/user/update/")
    public String updateConfirm(@Valid AdminUpdateProfileBindingModel adminUpdateProfileBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @RequestParam("id") Long id, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminUpdateProfileBindingModel", bindingResult);
        }

        AdminUpdateProfileBindingModel baseUser = this.userService.findUserById(id);
        boolean existEmail = this.userService.existUserByEmail(adminUpdateProfileBindingModel.getEmail(), baseUser.getEmail());
        if (existEmail) {
            redirectAttributes.addFlashAttribute("emailExists", true);
        }

        boolean existPhone = this.userService.existUserByPhoneNumber(adminUpdateProfileBindingModel.getPhoneNumber(), baseUser.getPhoneNumber());
        if (existPhone) {
            redirectAttributes.addFlashAttribute("phoneNumberExists", true);
        }

        if (bindingResult.hasErrors() || existEmail || existPhone) {
            redirectAttributes.addFlashAttribute("adminUpdateProfileBindingModel", adminUpdateProfileBindingModel);

            return "redirect:/admin/user/update/?id=" + id;
        }

        this.userService.adminUpdateProfile(adminUpdateProfileBindingModel, baseUser);

        return "redirect:/admin/user/?id=" + id;
    }

}
