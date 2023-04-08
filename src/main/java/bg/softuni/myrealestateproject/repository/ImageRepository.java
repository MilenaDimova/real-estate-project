package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.ImageEntity;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.view.ImageViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query("SELECT i.id FROM ImageEntity as i WHERE i.offer.id = :offerId ORDER BY i.imageOrder")
    List<Long> findAllImagesByOfferId(Long offerId);

    @Query("SELECT i FROM ImageEntity AS i WHERE i.offer.id = :id")
    List<ImageEntity> getAllImagesWithOfferId(Long id);

}
