package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ModelAndView home(ModelAndView modelAndView, @PageableDefault(sort = {"activeFrom", "price"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable) {
        modelAndView.addObject("offers", this.offerService.findLatestOffers(pageable));
        modelAndView.addObject("controllerAction", "");
        modelAndView.setViewName("index");

        return modelAndView;
    }


    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("offer", this.offerService.findById(id));
        modelAndView.setViewName("offer-detail");
        return modelAndView;
    }

}
