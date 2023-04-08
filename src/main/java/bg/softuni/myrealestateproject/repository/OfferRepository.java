package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import bg.softuni.myrealestateproject.model.entity.StatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {

    @Query("SELECT o FROM OfferEntity as o WHERE o.status.statusType = 'ACTIVE' AND o.activeFrom <= CURRENT_DATE")
    Page<OfferEntity> findAllActiveOffers(Pageable pageable);

    Page<OfferEntity> findAllByStatusAndOfferTypeAndActiveFromLessThanEqual(StatusEntity statusEntity, OfferTypeEntity offerType, LocalDate date, Pageable pageable);

    @Query("SELECT MAX(o.quadrature) FROM OfferEntity as o")
    Float findMaxQuadrature();

    @Query("SELECT MAX(o.price) FROM OfferEntity as o")
    BigDecimal findMaxPrice();

    List<OfferEntity> findAllByStatusAndActiveFromIsBefore(StatusEntity status, LocalDate date);

}
