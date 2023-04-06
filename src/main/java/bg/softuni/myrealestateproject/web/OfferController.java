package bg.softuni.myrealestateproject.web;

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
    public String add(Model model) {
        if (!model.containsAttribute("offerAddBindingModel")) {
            model.addAttribute("offerAddBindingModel", new OfferAddBindingModel());
        }
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
        OfferViewModel offer = this.offerService.findById(id);

        modelAndView.addObject("offer", offer);
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


    @PreAuthorize("@offerService.isOwner(#principal.name, #id) || @offerService.isAdmin(#principal.name)")
    @GetMapping("/update/")
    public String update(@RequestParam("id") Long id, Model model, Principal principal) {
        if (!model.containsAttribute("offerAddBindingModel")) {
            model.addAttribute("offerAddBindingModel", this.offerService.findById(id));
        }

        return "update-offer";
    }

    @PreAuthorize("@offerService.isOwner(#principal.name, #id) || @offerService.isAdmin(#principal.name)")
    @PutMapping ("/update/")
    public String updateConfirm(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @RequestParam("id") Long id, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);

            return "redirect:/offers/update/?id=" + id;
        }

        boolean isCurrentUserHasAdminRole = this.offerService.isAdmin(principal.getName());
        this.offerService.updateOffer(offerAddBindingModel, isCurrentUserHasAdminRole);

        return "redirect:/";
    }

    @PreAuthorize("@offerService.isOwner(#principal.name, #id) || @offerService.isAdmin(#principal.name)")
    @PutMapping("/republish/")
    public String republish(Principal principal, @RequestParam("id") Long id) {
        this.offerService.republishOffer(id);

        return "redirect:/";
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
