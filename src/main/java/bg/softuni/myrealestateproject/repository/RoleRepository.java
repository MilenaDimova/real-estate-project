package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.entity.RoleEntity;
import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findRoleEntityByRoleType(RoleTypeEnum roleType);
}
