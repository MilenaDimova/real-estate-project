package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "estates_types")
public class EstateTypeEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstateTypeEnum estateType;

    public EstateTypeEntity() {
    }

    public EstateTypeEntity(EstateTypeEnum estateType) {
        this.estateType = estateType;
    }

    public EstateTypeEnum getEstateType() {
        return estateType;
    }

    public EstateTypeEntity setEstateType(EstateTypeEnum estateType) {
        this.estateType = estateType;
        return this;
    }
}
