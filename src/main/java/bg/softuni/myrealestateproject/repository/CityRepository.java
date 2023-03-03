package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.CityEntity;
import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findCityEntityByCity(CityNameEnum city);
}
