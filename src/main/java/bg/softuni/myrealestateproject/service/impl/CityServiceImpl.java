package bg.softuni.myrealestateproject.service.impl;

import bg.softuni.myrealestateproject.model.entity.CityEntity;
import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import bg.softuni.myrealestateproject.repository.CityRepository;
import bg.softuni.myrealestateproject.service.CityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void initCities() {
        if(this.cityRepository.count() == 0) {
            Arrays.stream(CityNameEnum.values())
                    .forEach(cityNameEnum -> {
                        this.cityRepository.save(new CityEntity(cityNameEnum));
                    });
        }
    }

    @Override
    public CityEntity findCity(CityNameEnum cityNameEnum) {
        return this.cityRepository.findCityEntityByCity(cityNameEnum)
                .orElse(null);
    }
}
