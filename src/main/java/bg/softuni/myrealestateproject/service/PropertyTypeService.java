package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.PropertyTypeEntity;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;
import bg.softuni.myrealestateproject.repository.PropertyTypeRepository;
import bg.softuni.myrealestateproject.service.DataBaseInitService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PropertyTypeService implements DataBaseInitService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(PropertyTypeEnum.values())
                .forEach(propertyTypeEnum -> {
                    this.propertyTypeRepository.save(new PropertyTypeEntity(propertyTypeEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.propertyTypeRepository.count() == 0;
    }

    public PropertyTypeEntity findPropertyType(PropertyTypeEnum propertyTypeEnum) {
        return this.propertyTypeRepository.findPropertyTypeEntityByPropertyType(propertyTypeEnum)
                .orElse(null);
    }
}
