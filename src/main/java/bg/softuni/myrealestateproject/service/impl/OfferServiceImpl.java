package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.service.OfferServiceModel;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;
import bg.softuni.myrealestateproject.repository.OfferRepository;
import bg.softuni.myrealestateproject.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public List<OfferViewModel> findByOfferType(OfferTypeEnum offerTypeEnum) {
        OfferTypeEntity offerTypeEntity = this.offerTypeService.findOfferType(offerTypeEnum);
        return this.offerRepository.findAllByOfferType(offerTypeEntity)
                .stream()
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType().name());

                    return offerViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OfferViewModel findById(Long id) {

        return this.offerRepository.findById(id)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);

                    offerViewModel.setEstateType(offerEntity.getEstateType().getEstateType().name());
                    offerViewModel.setPropertyType(offerEntity.getPropertyType().getPropertyType().name());

                    return offerViewModel;
                })
                .orElse(null);
    }

    @Override
    public List<OfferViewModel> findLatestOffers() {
        return this.offerRepository.findTop6ByOrderByActiveFromDesc()
                .stream()
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType().name());

                    return offerViewModel;
                })
                .collect(Collectors.toList());
    }
}
