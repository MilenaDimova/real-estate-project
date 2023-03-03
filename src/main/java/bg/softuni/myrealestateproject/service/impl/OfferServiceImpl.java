package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.service.OfferServiceModel;
import bg.softuni.myrealestateproject.repository.OfferRepository;
import bg.softuni.myrealestateproject.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CityService cityService;
    private final OfferTypeService offerTypeService;
    private final EstateTypeService estateTypeService;
    private final PropertyTypeService propertyTypeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, CityService cityService, OfferTypeService offerTypeService,
                            EstateTypeService estateTypeService, PropertyTypeService propertyTypeService,
            UserService userService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.cityService = cityService;
        this.offerTypeService = offerTypeService;
        this.estateTypeService = estateTypeService;
        this.propertyTypeService = propertyTypeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addOffer(OfferServiceModel offerServiceModel) {
        OfferEntity offer = this.modelMapper.map(offerServiceModel, OfferEntity.class);

        offer.setCity(this.cityService.findCity(offerServiceModel.getCity()));
        offer.setOfferType(this.offerTypeService.findOfferType(offerServiceModel.getOfferType()));
        offer.setEstateType(this.estateTypeService.findEstateType(offerServiceModel.getEstateType()));
        offer.setPropertyType(this.propertyTypeService.findPropertyType(offerServiceModel.getPropertyType()));
        offer.setOwner(this.userService.findById(offerServiceModel.getOwnerId()));

        this.offerRepository.saveAndFlush(offer);
    }
}
