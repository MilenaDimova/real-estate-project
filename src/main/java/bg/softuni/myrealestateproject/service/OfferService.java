package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.service.OfferServiceModel;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;

import java.util.List;

public interface OfferService {
    void addOffer(OfferServiceModel offerServiceModel);

    List<OfferViewModel> findAllOffers();

}
