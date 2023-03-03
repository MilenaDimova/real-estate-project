package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.EstateTypeEntity;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;

public interface EstateTypeService {
    void initEstateType();

    EstateTypeEntity findEstateType(EstateTypeEnum estateTypeEnum);
}
