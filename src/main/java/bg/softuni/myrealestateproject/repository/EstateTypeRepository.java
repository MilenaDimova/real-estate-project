package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.EstateTypeEntity;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstateTypeRepository extends JpaRepository<EstateTypeEntity, Long> {


    Optional<EstateTypeEntity> findEstateTypeEntityByEstateType(EstateTypeEnum estateType);
}
