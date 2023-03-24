package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.service.OfferService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

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
    public String add() {
        return "add-offer";
    }

    @PostMapping("/add")
    private String addConfirm(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);

            return "redirect:add";
        }

        this.offerService.addOffer(offerAddBindingModel, userDetails);

        return "redirect:/";

    }

    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }

    @GetMapping("/sales")
    public ModelAndView sales(ModelAndView modelAndView, @PageableDefault(sort = "price", page = 0, size = 4) Pageable pageable) {
        modelAndView.addObject("controllerAction", "sales");
        return setModelAndView(modelAndView, OfferTypeEnum.SALE, pageable);
    }


    @GetMapping("/rents")
    public ModelAndView rents(ModelAndView modelAndView, @PageableDefault(sort = "price", page = 0, size = 4) Pageable pageable) {

        modelAndView.addObject("controllerAction", "rents");
        return setModelAndView(modelAndView, OfferTypeEnum.RENT, pageable);
    }

    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        modelAndView.addObject("offer", this.offerService.findById(id));
        modelAndView.setViewName("offer-detail");

        return modelAndView;
    }


    private ModelAndView setModelAndView(ModelAndView modelAndView, OfferTypeEnum offerTypeEnum, Pageable pageable) {
        modelAndView.addObject("offers", this.offerService.findByOfferType(offerTypeEnum, pageable));
        modelAndView.setViewName("offers");

        return modelAndView;
    }


    @GetMapping("/update/")
    public ModelAndView update(@RequestParam("id") Long id, ModelAndView modelAndView,
                               @Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult, Map<String, Object> modelMap) {
        if (offerAddBindingModel.isHasErrors()) {
            modelAndView.addObject("property", offerAddBindingModel);
        } else {
            var offerAddBindingModelDB = this.offerService.updateOfferById(id);
            modelMap.put("offerAddBindingModel", offerAddBindingModelDB);
            modelAndView.addObject("property", offerAddBindingModelDB);
        }

        modelAndView.setViewName("update-offer");

        return modelAndView;
    }

    @PostMapping("/update/")
    public ModelAndView updateConfirm(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);
            offerAddBindingModel.setHasErrors(true);
            modelAndView.setViewName(String.format("redirect:/offers/update/?id=%d", offerAddBindingModel.getId()));
            return modelAndView;
        }
        offerAddBindingModel.setHasErrors(false);
        offerAddBindingModel =  this.offerService.updateOffer(offerAddBindingModel);
        if (offerAddBindingModel.isHasErrors()) {
            //modelAndView.setViewName("Some error page....");
            var a = 1;
        } else {
            modelAndView.setViewName(String.format("redirect:/offers/details/?id=%d", offerAddBindingModel.getId()));
        }

        return modelAndView;
    }
}
