package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferTypeRepository extends JpaRepository<OfferTypeEntity, Long> {

    Optional<OfferTypeEntity> findOfferTypeEntityByOfferType(OfferTypeEnum offerType);
}
