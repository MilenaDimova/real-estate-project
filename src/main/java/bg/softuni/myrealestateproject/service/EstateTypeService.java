package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.EstateTypeEntity;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import bg.softuni.myrealestateproject.repository.EstateTypeRepository;
import bg.softuni.myrealestateproject.service.EstateTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EstateTypeService implements DataBaseInitService{

    private final EstateTypeRepository estateTypeRepository;

    public EstateTypeService(EstateTypeRepository estateTypeRepository) {
        this.estateTypeRepository = estateTypeRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(EstateTypeEnum.values())
                .forEach(estateTypeEnum -> {
                    this.estateTypeRepository.save(new EstateTypeEntity(estateTypeEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.estateTypeRepository.count() == 0;
    }

    public EstateTypeEntity findEstateType(EstateTypeEnum estateTypeEnum) {
        return this.estateTypeRepository.findEstateTypeEntityByEstateType(estateTypeEnum)
                .orElse(null);
    }
}
