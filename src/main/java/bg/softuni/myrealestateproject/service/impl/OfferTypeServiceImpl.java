package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.repository.OfferTypeRepository;
import bg.softuni.myrealestateproject.service.OfferTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OfferTypeServiceImpl implements OfferTypeService {

    private final OfferTypeRepository offerTypeRepository;

    public OfferTypeServiceImpl(OfferTypeRepository offerTypeRepository) {
        this.offerTypeRepository = offerTypeRepository;
    }

    @Override
    public void initOfferType() {
        if (this.offerTypeRepository.count() == 0) {
            Arrays.stream(OfferTypeEnum.values())
                    .forEach(offerTypeEnum -> {
                        this.offerTypeRepository.save(new OfferTypeEntity(offerTypeEnum));
                    });
        }
    }

    @Override
    public OfferTypeEntity findOfferType(OfferTypeEnum offerTypeEnum) {
        return this.offerTypeRepository.findOfferTypeEntityByOfferType(offerTypeEnum)
                .orElse(null);
    }
}
