package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.PropertyTypeEntity;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyTypeEntity, Long> {

    Optional<PropertyTypeEntity> findPropertyTypeEntityByPropertyType(PropertyTypeEnum propertyType);
}
