package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.StatusEntity;
import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    @Query("SELECT s FROM StatusEntity as s WHERE s.statusType = :statusType")
    StatusEntity findByStatusType(@Param("statusType") StatusTypeEnum statusType);
}
