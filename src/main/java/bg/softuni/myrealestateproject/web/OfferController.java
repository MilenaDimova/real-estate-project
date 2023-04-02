package bg.softuni.myrealestateproject.web;

import bg.softuni.myrealestateproject.exception.ObjectNotFoundException;
import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.binding.SearchOfferBindingModel;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.service.CurrentUser;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;
import bg.softuni.myrealestateproject.service.ImageService;
import bg.softuni.myrealestateproject.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final ImageService imageService;

    public OfferController(OfferService offerService, ImageService imageService) {
        this.offerService = offerService;
        this.imageService = imageService;
    }

    @GetMapping("/add")
    public String add() {
        return "add-offer";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, @AuthenticationPrincipal CurrentUser userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);

            return "redirect:add";
        }

        this.offerService.addOffer(offerAddBindingModel, userDetails);

        return "add-offer-message";

    }

    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }

    @GetMapping("/sales")
    public ModelAndView sales(ModelAndView modelAndView, @PageableDefault(sort = "price", size = 4) Pageable pageable) {
        modelAndView.addObject("controllerAction", "sales");
        return setModelAndView(modelAndView, OfferTypeEnum.SALE, pageable);
    }


    @GetMapping("/rents")
    public ModelAndView rents(ModelAndView modelAndView, @PageableDefault(sort = "price", size = 4) Pageable pageable) {

        modelAndView.addObject("controllerAction", "rents");
        return setModelAndView(modelAndView, OfferTypeEnum.RENT, pageable);
    }

    @GetMapping("/details/")
    public ModelAndView details(@RequestParam("id") Long id, ModelAndView modelAndView) {

        OfferViewModel optionalOffer = this.offerService.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " was not found!"));

        modelAndView.addObject("offer", optionalOffer);
        modelAndView.setViewName("offer-detail");

        return modelAndView;
    }

    @PreAuthorize("@offerService.isOwner(#principal.name, #id) || @offerService.isAdmin(#principal.name)")
    @DeleteMapping("/delete/")
    public String delete(Principal principal, @RequestParam("id") Long id) {
        this.imageService.deleteAllImagesByOfferId(id);
        this.offerService.deleteOfferById(id);

        return "redirect:/";
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, OfferTypeEnum offerTypeEnum, Pageable pageable) {
        modelAndView.addObject("offers", this.offerService.findByActiveStatusAndOfferType(offerTypeEnum, pageable));
        modelAndView.setViewName("offers");

        return modelAndView;
    }


    @PreAuthorize("@offerService.isOwner(#principal.name, #offerAddBindingModel.id) || @offerService.isAdmin(#principal.name)")
    @GetMapping("/update/")
    public ModelAndView update(Principal principal, @RequestParam("id") Long id, ModelAndView modelAndView,
                               @Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult, Map<String, Object> modelMap) {
        if (offerAddBindingModel.isHasErrors()) {
            modelMap.put("offerAddBindingModel", offerAddBindingModel);
            modelAndView.addObject("property", offerAddBindingModel);
        } else {
            var offerAddBindingModelDB = this.offerService.updateOfferById(id);
            modelMap.put("offerAddBindingModel", offerAddBindingModelDB);
            modelAndView.addObject("property", offerAddBindingModelDB);
        }

        modelAndView.setViewName("update-offer");

        return modelAndView;
    }

    @PreAuthorize("@offerService.isOwner(#principal.name, #offerAddBindingModel.id) || @offerService.isAdmin(#principal.name)")
    @PutMapping ("/update/")
    public ModelAndView updateConfirm(Principal principal, @Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, ModelAndView modelAndView, @AuthenticationPrincipal CurrentUser userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);
            offerAddBindingModel.setHasErrors(true);
            modelAndView.setViewName(String.format("redirect:/offers/update/?id=%d", offerAddBindingModel.getId()));
            return modelAndView;
        }
        offerAddBindingModel.setHasErrors(false);
        offerAddBindingModel =  this.offerService.updateOffer(offerAddBindingModel, userDetails.isAdmin());
        if (offerAddBindingModel.isHasErrors()) {
            //modelAndView.setViewName("Some error page....");
        } else {
            modelAndView.setViewName(String.format("redirect:/offers/details/?id=%d", offerAddBindingModel.getId()));
        }

        return modelAndView;
    }

    @GetMapping("/search")
    public String search(@Valid SearchOfferBindingModel searchOfferBindingModel, BindingResult bindingResult, Model model) {
        model.addAttribute("maxQuadratureDefault", this.offerService.findMaxQuadrature());
        model.addAttribute("maxPriceDefault", this.offerService.findMaxPrice());

        if (bindingResult.hasErrors()) {
            model.addAttribute("searchOfferBindingModel", searchOfferBindingModel);
            model.addAttribute("org.springframework.validation.BindingResult.searchOfferBindingModel", bindingResult);

            return "search-offers";
        }

        if (!model.containsAttribute("searchOfferBindingModel")) {
            model.addAttribute("searchOfferBindingModel", searchOfferBindingModel);
        }

        if (!searchOfferBindingModel.isEmpty()) {
            model.addAttribute("offers", offerService.searchOffers(searchOfferBindingModel));
        }

        return "search-offers";
    }
}
