package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;

public interface OfferTypeService {
    void initOfferType();

    OfferTypeEntity findOfferType(OfferTypeEnum offerTypeEnum);
}
