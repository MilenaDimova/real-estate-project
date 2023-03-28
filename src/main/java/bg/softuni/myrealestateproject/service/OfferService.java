package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.binding.SearchOfferBindingModel;
import bg.softuni.myrealestateproject.model.entity.*;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;
import bg.softuni.myrealestateproject.model.view.OwnerViewModel;
import bg.softuni.myrealestateproject.repository.OfferRepository;
import bg.softuni.myrealestateproject.repository.OfferSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.modelmapper.Converters.Collection.map;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final CityService cityService;
    private final OfferTypeService offerTypeService;
    private final EstateTypeService estateTypeService;
    private final PropertyTypeService propertyTypeService;
    private final ImageService imageService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, CityService cityService, OfferTypeService offerTypeService,
                        EstateTypeService estateTypeService, PropertyTypeService propertyTypeService,
                        ImageService imageService, UserService userService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.cityService = cityService;
        this.offerTypeService = offerTypeService;
        this.estateTypeService = estateTypeService;
        this.propertyTypeService = propertyTypeService;
        this.imageService = imageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public Optional<OfferViewModel> findById(Long id) {

        return this.offerRepository.findById(id)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);

                    offerViewModel.setEstateType(offerEntity.getEstateType().getEstateType().name());
                    offerViewModel.setPropertyType(offerEntity.getPropertyType().getPropertyType().name());
                    offerViewModel.setOwner(this.modelMapper.map(offerEntity.getOwner(), OwnerViewModel.class));
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                });
    }

    public boolean isOwner(String username, Long offerId) {
        boolean isOwner = this.offerRepository.findById(offerId)
                .filter(o -> o.getOwner().getEmail().equals(username))
                .isPresent();

        if (isOwner) {
            return true;
        }

        return this.userService.findByEmail(username)
                .filter(this::isAdmin)
                .isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleType() == RoleTypeEnum.ADMIN);
    }

    public void deleteOfferById(Long id) {
        this.offerRepository.deleteById(id);
    }

    public List<OfferViewModel> findLatestOffers() {
        return this.offerRepository.findTop6ByOrderByActiveFromDesc()
                .stream()
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType().name());
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                })
                .collect(Collectors.toList());
    }

    public Page<OfferViewModel> findByOfferType(OfferTypeEnum offerTypeEnum, Pageable pageable) {
        OfferTypeEntity offerTypeEntity = this.offerTypeService.findOfferType(offerTypeEnum);
        return this.offerRepository.findAllByOfferType(offerTypeEntity, pageable)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType().name());
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                });
    }

    public void addOffer(OfferAddBindingModel offerAddBindingModel, UserDetails userDetails) {
        OfferEntity offer = this.modelMapper.map(offerAddBindingModel, OfferEntity.class);

        UserEntity owner = this.userService.findByEmail(userDetails.getUsername()).orElse(null);

        offer.setCity(this.cityService.findCity(offerAddBindingModel.getCity()));
        offer.setOfferType(this.offerTypeService.findOfferType(offerAddBindingModel.getOfferType()));
        offer.setEstateType(this.estateTypeService.findEstateType(offerAddBindingModel.getEstateType()));
        offer.setPropertyType(this.propertyTypeService.findPropertyType(offerAddBindingModel.getPropertyType()));
        offer.setOwner(owner);

        OfferEntity offerEntity = this.offerRepository.save(offer);
        offer.setImages(this.imageService.saveImageToDB(offerAddBindingModel.getUploadedImages(), offerEntity));
    }


    public OfferAddBindingModel updateOfferById(Long id) {
        return this.offerRepository.findById(id)
                .map(offerEntity -> {
                    OfferAddBindingModel offerAddBindingModel = this.modelMapper.map(offerEntity, OfferAddBindingModel.class);
                    offerAddBindingModel.setOfferType(offerEntity.getOfferType().getOfferType());
                    offerAddBindingModel.setEstateType(offerEntity.getEstateType().getEstateType());
                    offerAddBindingModel.setPropertyType(offerEntity.getPropertyType().getPropertyType());
                    return offerAddBindingModel;
                }).orElse(null);
    }


    public OfferAddBindingModel updateOffer(OfferAddBindingModel offerAddBindingModel) {
        Optional<OfferEntity> offerEntity = this.offerRepository.findById(offerAddBindingModel.getId());

        try {
            if (offerEntity.isPresent()) {
                this.modelMapper.map(offerAddBindingModel, offerEntity.get());
                offerEntity.get().setCity(this.cityService.findCity(offerAddBindingModel.getCity()));
                offerEntity.get().setOfferType(this.offerTypeService.findOfferType(offerAddBindingModel.getOfferType()));
                offerEntity.get().setEstateType(this.estateTypeService.findEstateType(offerAddBindingModel.getEstateType()));
                offerEntity.get().setPropertyType(this.propertyTypeService.findPropertyType(offerAddBindingModel.getPropertyType()));
            }
            this.offerRepository.saveAndFlush(offerEntity.get());
        } catch (Exception ex) {
            offerAddBindingModel.setHasErrors(true);
        }

        return offerAddBindingModel;
    }


    public List<OfferViewModel> searchOffers(SearchOfferBindingModel searchOfferBindingModel) {
        OfferSpecification specification = new OfferSpecification(searchOfferBindingModel);
        List<OfferEntity> filteredOffers = this.offerRepository.findAll(specification);

        return filteredOffers.stream()
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType().name());
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                })
                .collect(Collectors.toList());
    }

    public Float findMaxQuadrature() {
        return this.offerRepository.findMaxQuadrature();
    }

    public BigDecimal findMaxPrice() {
        return this.offerRepository.findMaxPrice();
    }
}
