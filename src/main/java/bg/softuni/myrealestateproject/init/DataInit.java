package bg.softuni.myrealestateproject.init;

import bg.softuni.myrealestateproject.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final OfferTypeService offerTypeService;
    private final EstateTypeService estateTypeService;
    private final PropertyTypeService propertyTypeService;
    private final RoleService roleService;
    private final CityService cityService;

    public DataInit(OfferTypeService offerTypeService, EstateTypeService estateTypeService, PropertyTypeService propertyTypeService, RoleService roleService, CityService cityService) {
        this.offerTypeService = offerTypeService;
        this.estateTypeService = estateTypeService;
        this.propertyTypeService = propertyTypeService;
        this.roleService = roleService;
        this.cityService = cityService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.roleService.initRoles();
        this.cityService.initCities();
        this.offerTypeService.initOfferType();
        this.estateTypeService.initEstateType();
        this.propertyTypeService.initPropertyType();
    }
}
