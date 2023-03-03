package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.service.OfferServiceModel;
import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.service.UserServiceModel;
import bg.softuni.myrealestateproject.service.OfferService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/users/signin";
        }

        return "add-offer";
    }

    @PostMapping("/add")
    private String addConfirm(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);

            return "redirect:add";
        }

        OfferServiceModel offerServiceModel = this.modelMapper.map(offerAddBindingModel, OfferServiceModel.class);
        offerServiceModel.setOwnerId(((UserServiceModel)httpSession.getAttribute("user")).getId());

        this.offerService.addOffer(offerServiceModel);

        return "redirect:/index";

    }

    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }
}
