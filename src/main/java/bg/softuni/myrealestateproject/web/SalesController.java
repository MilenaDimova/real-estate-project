package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.service.OfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offers")
public class SalesController {
    private final OfferService offerService;

    public SalesController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/sales")
    public ModelAndView sales(HttpSession httpSession, ModelAndView modelAndView) {
        if(httpSession.getAttribute("user") == null) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.addObject("offers", this.offerService.findAllSaleOffers());
            modelAndView.setViewName("sales");
        }

        return modelAndView;
    }

    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("property", this.offerService.findById(id));
        modelAndView.setViewName("property-detail");

        return modelAndView;
    }

}
