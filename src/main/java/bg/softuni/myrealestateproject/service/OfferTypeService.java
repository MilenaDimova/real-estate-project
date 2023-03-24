package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.repository.OfferTypeRepository;
import bg.softuni.myrealestateproject.service.DataBaseInitService;
import bg.softuni.myrealestateproject.service.OfferTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OfferTypeService implements DataBaseInitService {

    private final OfferTypeRepository offerTypeRepository;

    public OfferTypeService(OfferTypeRepository offerTypeRepository) {
        this.offerTypeRepository = offerTypeRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(OfferTypeEnum.values())
                .forEach(offerTypeEnum -> {
                    this.offerTypeRepository.save(new OfferTypeEntity(offerTypeEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.offerTypeRepository.count() == 0;
    }

    public OfferTypeEntity findOfferType(OfferTypeEnum offerTypeEnum) {
        return this.offerTypeRepository.findOfferTypeEntityByOfferType(offerTypeEnum)
                .orElse(null);
    }
}
