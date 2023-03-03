package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.EstateTypeEntity;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import bg.softuni.myrealestateproject.repository.EstateTypeRepository;
import bg.softuni.myrealestateproject.service.EstateTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EstateTypeServiceImpl implements EstateTypeService {

    private final EstateTypeRepository estateTypeRepository;

    public EstateTypeServiceImpl(EstateTypeRepository estateTypeRepository) {
        this.estateTypeRepository = estateTypeRepository;
    }


    @Override
    public void initEstateType() {
        if (this.estateTypeRepository.count() == 0) {
            Arrays.stream(EstateTypeEnum.values())
                    .forEach(estateTypeEnum -> {
                        this.estateTypeRepository.save(new EstateTypeEntity(estateTypeEnum));
                    });
        }
    }

    @Override
    public EstateTypeEntity findEstateType(EstateTypeEnum estateTypeEnum) {
        return this.estateTypeRepository.findEstateTypeEntityByEstateType(estateTypeEnum)
                .orElse(null);
    }
}
