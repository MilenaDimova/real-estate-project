package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.service.OfferServiceModel;
import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.service.UserServiceModel;
import bg.softuni.myrealestateproject.service.OfferService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/sales")
    public ModelAndView sales(HttpSession httpSession, ModelAndView modelAndView) {
        return setModelAndView(httpSession, modelAndView, OfferTypeEnum.SALE);
    }

    @GetMapping("/rents")
    public ModelAndView rents(HttpSession httpSession, ModelAndView modelAndView) {
        return setModelAndView(httpSession, modelAndView, OfferTypeEnum.RENT);
    }

    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("property", this.offerService.findById(id));
        modelAndView.setViewName("offer-detail");

        return modelAndView;
    }

    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }

    private ModelAndView setModelAndView(HttpSession httpSession, ModelAndView modelAndView, OfferTypeEnum offerTypeEnum) {
        if(httpSession.getAttribute("user") == null) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.addObject("offers", this.offerService.findByOfferType(offerTypeEnum));
            modelAndView.setViewName("offers");
        }

        return modelAndView;
    }

}
