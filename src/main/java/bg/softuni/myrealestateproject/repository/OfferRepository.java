package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.entity.OfferTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {

    List<OfferEntity> findTop6ByOrderByActiveFromDesc();


//    @Query("SELECT o FROM OfferEntity as o WHERE o.isApproved = true")
//    List<OfferEntity> findAllApprovedOffers();

    Page<OfferEntity> findAllByOfferType(OfferTypeEntity offerType, Pageable pageable);

    @Query("SELECT MAX(o.quadrature) FROM OfferEntity as o")
    Float findMaxQuadrature();

    @Query("SELECT MAX(o.price) FROM OfferEntity as o")
    BigDecimal findMaxPrice();
}
