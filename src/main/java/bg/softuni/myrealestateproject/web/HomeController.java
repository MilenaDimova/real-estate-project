package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final OfferService offerService;

    public HomeController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("offers", this.offerService.findLatestOffers());
        modelAndView.setViewName("index");

        return modelAndView;
    }


    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("property", this.offerService.findById(id));
        modelAndView.setViewName("offer-detail");
        return modelAndView;
    }

//    @GetMapping("/")
//    public String allOffers(Model model, @PageableDefault(page = 0, size = 5) Pageable pageable) {
//        model.addAttribute("offers", this.offerService.getAllOffers(pageable));
//        return "index";
//    }
}
