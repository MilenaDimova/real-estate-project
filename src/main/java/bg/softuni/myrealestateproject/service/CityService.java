package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.CityEntity;
import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import bg.softuni.myrealestateproject.repository.CityRepository;
import bg.softuni.myrealestateproject.service.DataBaseInitService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CityService implements DataBaseInitService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void dbInit() {
        Arrays.stream(CityNameEnum.values())
                .forEach(cityNameEnum -> {
                    this.cityRepository.save(new CityEntity(cityNameEnum));
                });
    }

    @Override
    public boolean isDbInit() {
        return this.cityRepository.count() == 0;
    }

    public CityEntity findCity(CityNameEnum cityNameEnum) {
        return this.cityRepository.findCityEntityByCity(cityNameEnum)
                .orElse(null);
    }
}
