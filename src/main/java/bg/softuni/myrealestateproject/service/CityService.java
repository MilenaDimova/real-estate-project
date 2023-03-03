package bg.softuni.myrealestateproject.service;

import bg.softuni.myrealestateproject.model.entity.CityEntity;
import bg.softuni.myrealestateproject.model.enums.CityNameEnum;

public interface CityService {
    void initCities();

    CityEntity findCity(CityNameEnum cityNameEnum);
}
