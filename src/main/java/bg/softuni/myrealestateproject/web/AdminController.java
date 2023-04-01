package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.service.ImageService;
import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OfferService offerService;
    private final ImageService imageService;

    public AdminController(OfferService offerService, ImageService imageService) {
        this.offerService = offerService;
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
}
