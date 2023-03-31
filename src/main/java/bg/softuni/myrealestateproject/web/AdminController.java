package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OfferService offerService;

    public AdminController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/offers")
    public ModelAndView adminOffers(ModelAndView modelAndView, @PageableDefault(size = 6) Pageable pageable) {
        modelAndView.addObject("offers", this.offerService.findAllOffers(pageable));
        modelAndView.addObject("controllerAction", "");
        modelAndView.setViewName("admin/offers");

        return modelAndView;
    }
}
