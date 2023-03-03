package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleTypeEnum roleType;

    public RoleEntity() {
    }

    public RoleEntity(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public RoleEntity setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
        return this;
    }
}
