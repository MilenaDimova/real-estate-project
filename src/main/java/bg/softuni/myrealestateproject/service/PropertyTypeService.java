package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.PropertyTypeEntity;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;

public interface PropertyTypeService {
    void initPropertyType();

    PropertyTypeEntity findPropertyType(PropertyTypeEnum propertyTypeEnum);
}
