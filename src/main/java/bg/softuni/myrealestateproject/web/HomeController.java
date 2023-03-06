package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final OfferService offerService;

    public HomeController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("index")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("properties", this.offerService.findLatestOffers());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("property", this.offerService.findById(id));
        modelAndView.setViewName("property-detail");

        return modelAndView;
    }

//    @PostMapping("/search")
//    public ModelAndView search(ModelAndView modelAndView) {
//
//    }
}
