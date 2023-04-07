package bg.softuni.myrealestateproject.utils;

import bg.softuni.myrealestateproject.model.entity.*;
import bg.softuni.myrealestateproject.model.enums.*;
import bg.softuni.myrealestateproject.repository.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class Helper {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final StatusRepository statusRepository;
    private final CityRepository cityRepository;
    private final OfferTypeRepository offerTypeRepository;
    private final EstateTypeRepository estateTypeRepository;
    private final PropertyTypeRepository propertyTypeRepository;

    public Helper(UserRepository userRepository, OfferRepository offerRepository, StatusRepository statusRepository, CityRepository cityRepository,
                  OfferTypeRepository offerTypeRepository, EstateTypeRepository estateTypeRepository, PropertyTypeRepository propertyTypeRepository) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.statusRepository = statusRepository;
        this.cityRepository = cityRepository;
        this.offerTypeRepository = offerTypeRepository;
        this.estateTypeRepository = estateTypeRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public OfferEntity createOfferOne() {
        OfferEntity offerEntity = new OfferEntity()
                .setStatus(this.statusRepository.findByStatusType(StatusTypeEnum.PENDING))
                .setArea("Center")
                .setPrice(BigDecimal.TEN)
                .setCity(this.cityRepository.findCityEntityByCity(CityNameEnum.SOFIA).get())
                .setOfferType(this.offerTypeRepository.findOfferTypeEntityByOfferType(OfferTypeEnum.RENT).get())
                .setEstateType(this.estateTypeRepository.findEstateTypeEntityByEstateType(EstateTypeEnum.SHOP).get())
                .setPropertyType(this.propertyTypeRepository.findPropertyTypeEntityByPropertyType(PropertyTypeEnum.KITCHENETTE).get())
                .setFloor(1)
                .setRoom(1)
                .setBed(1)
                .setBath(1)
                .setQuadrature(1)
                .setActiveFrom(LocalDate.now().plusDays(1))
                .setOwner(this.userRepository.findByEmail("admin@example.com").get());

        return this.offerRepository.save(offerEntity);
    }
}
