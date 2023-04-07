package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.exception.ForbiddenException;
import bg.softuni.myrealestateproject.exception.ObjectNotFoundException;
import bg.softuni.myrealestateproject.model.binding.OfferAddBindingModel;
import bg.softuni.myrealestateproject.model.binding.SearchOfferBindingModel;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.entity.StatusEntity;
import bg.softuni.myrealestateproject.model.entity.UserEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import bg.softuni.myrealestateproject.model.view.OfferViewModel;
import bg.softuni.myrealestateproject.model.view.OwnerViewModel;
import bg.softuni.myrealestateproject.repository.OfferRepository;
import bg.softuni.myrealestateproject.repository.OfferSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final CityService cityService;
    private final OfferTypeService offerTypeService;
    private final EstateTypeService estateTypeService;
    private final PropertyTypeService propertyTypeService;
    private final StatusService statusService;
    private final ImageService imageService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, CityService cityService, OfferTypeService offerTypeService,
                        EstateTypeService estateTypeService, PropertyTypeService propertyTypeService,
                        StatusService statusService, ImageService imageService, UserService userService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.cityService = cityService;
        this.offerTypeService = offerTypeService;
        this.estateTypeService = estateTypeService;
        this.propertyTypeService = propertyTypeService;
        this.statusService = statusService;
        this.imageService = imageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public OfferViewModel findById(Long id) {
        return this.offerRepository.findById(id)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setStatusType(offerEntity.getStatus().getStatusType());
                    offerViewModel.setCity(offerEntity.getCity().getCity());
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
                    offerViewModel.setEstateType(offerEntity.getEstateType().getEstateType());
                    offerViewModel.setPropertyType(offerEntity.getPropertyType().getPropertyType());
                    offerViewModel.setOwner(this.modelMapper.map(offerEntity.getOwner(), OwnerViewModel.class));
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                }).orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " was not found!"));
    }

    public boolean isOwner(String username, Long offerId) {
        return this.offerRepository.findById(offerId)
                .filter(o -> o.getOwner().getEmail().equals(username))
                .isPresent();
    }

    public boolean isAdmin(String username) {
        Optional<UserEntity> user = this.userService.findByEmail(username);
        return user.isPresent() && user.get().getRoles().stream().anyMatch(r -> r.getRoleType() == RoleTypeEnum.ADMIN);
    }

    public void deleteOfferById(Long id) {
        this.offerRepository.deleteById(id);
    }

    public Page<OfferViewModel> findLatestOffers(Pageable pageable) {
        return this.offerRepository.findAllActiveOffers(pageable)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
                    offerViewModel.setImagesIds(this.imageService.getImagesIds(offerEntity.getId()));

                    return offerViewModel;
                });
    }

    public Page<OfferViewModel> findByActiveStatusAndOfferType(OfferTypeEnum offerTypeEnum, Pageable pageable) {
        StatusEntity statusActiveEntity = this.statusService.findStatusActive();
        OfferTypeEntity offerTypeEntity = this.offerTypeService.findOfferType(offerTypeEnum);
        return this.offerRepository.findAllByStatusAndOfferTypeAndActiveFromLessThanEqual(statusActiveEntity, offerTypeEntity, LocalDate.now(), pageable)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
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
        if (owner != null && owner.getRoles().stream().anyMatch(r -> r.getRoleType() == RoleTypeEnum.ADMIN)) {
            offer.setStatus(this.statusService.findStatusType(offerAddBindingModel.getStatusType()));
        } else {
            offer.setStatus(this.statusService.findStatusPending());
        }
        offer.setOwner(owner);

        OfferEntity offerEntity = this.offerRepository.save(offer);
        if (offerAddBindingModel.getUploadedImages() != null) {
            offer.setImages(this.imageService.saveImageToDB(offerAddBindingModel.getUploadedImages(), offerEntity));
        }
    }

    public OfferAddBindingModel updateOffer(OfferAddBindingModel offerAddBindingModel, boolean isAdmin) {
        Optional<OfferEntity> offerEntity = this.offerRepository.findById(offerAddBindingModel.getId());

        try {
            if (offerEntity.isPresent()) {
                this.modelMapper.map(offerAddBindingModel, offerEntity.get());
                offerEntity.get().setCity(this.cityService.findCity(offerAddBindingModel.getCity()));
                offerEntity.get().setOfferType(this.offerTypeService.findOfferType(offerAddBindingModel.getOfferType()));
                offerEntity.get().setEstateType(this.estateTypeService.findEstateType(offerAddBindingModel.getEstateType()));
                offerEntity.get().setPropertyType(this.propertyTypeService.findPropertyType(offerAddBindingModel.getPropertyType()));
                if (isAdmin) {
                    offerEntity.get().setStatus(this.statusService.findStatusType(offerAddBindingModel.getStatusType()));
                }
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
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
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

    public Page<OfferViewModel> findAllOffers(Pageable pageable) {
        return this.offerRepository.findAll(pageable)
                .map(offerEntity -> {
                    OfferViewModel offerViewModel = this.modelMapper.map(offerEntity, OfferViewModel.class);
                    offerViewModel.setCity(offerEntity.getCity().getCity());
                    offerViewModel.setEstateType(offerEntity.getEstateType().getEstateType());
                    offerViewModel.setStatusType(offerEntity.getStatus().getStatusType());
                    offerViewModel.setOfferType(offerEntity.getOfferType().getOfferType());
                    offerViewModel.setActiveFrom(offerEntity.getActiveFrom().toString());

                    return offerViewModel;
                });

    }

    public void republishOffer(Long id) {
        OfferEntity offerEntity = this.offerRepository.findById(id).get();
        if (offerEntity.getStatus().getStatusType() == StatusTypeEnum.EXPIRED) {
            offerEntity.setStatus(this.statusService.findStatusActive());
            offerEntity.setActiveFrom(LocalDate.now());

            this.offerRepository.saveAndFlush(offerEntity);
        } else {
            throw new ForbiddenException("You don't have permissions to republish this offer!");
        }
    }

    public void findAllActiveStatusOffersGreaterThanMonth() {
        List<OfferEntity> allByStatusAndActiveFromBefore = this.offerRepository.findAllActiveStatusOffersGreaterThanMonth();
        List<OfferEntity> expiredStatusOffers = allByStatusAndActiveFromBefore.stream()
                .map(offerEntity -> offerEntity.setStatus(this.statusService.findStatusType(StatusTypeEnum.EXPIRED))).toList();

        this.offerRepository.saveAllAndFlush(expiredStatusOffers);
    }
}
