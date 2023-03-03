package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {


    @Query("SELECT o from OfferEntity as o WHERE o.offerType.offerType = 'SALE'")
    List<OfferEntity> findAllSalesOffers();
}
