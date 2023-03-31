package bg.softuni.myrealestateproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/offers")
    public String adminOffers() {
        return "/admin/offers";
    }
}
