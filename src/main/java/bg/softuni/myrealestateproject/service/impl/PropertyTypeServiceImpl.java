package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.PropertyTypeEntity;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;
import bg.softuni.myrealestateproject.repository.PropertyTypeRepository;
import bg.softuni.myrealestateproject.service.PropertyTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PropertyTypeServiceImpl implements PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeServiceImpl(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }


    @Override
    public void initPropertyType() {
        if (this.propertyTypeRepository.count() == 0) {
            Arrays.stream(PropertyTypeEnum.values())
                    .forEach(propertyTypeEnum -> {
                        this.propertyTypeRepository.save(new PropertyTypeEntity(propertyTypeEnum));
                    });
        }
    }

    @Override
    public PropertyTypeEntity findPropertyType(PropertyTypeEnum propertyTypeEnum) {
        return this.propertyTypeRepository.findPropertyTypeEntityByPropertyType(propertyTypeEnum)
                .orElse(null);
    }
}
