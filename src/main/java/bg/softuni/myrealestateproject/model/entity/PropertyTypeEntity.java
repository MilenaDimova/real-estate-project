package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "property_type")
public class PropertyTypeEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyTypeEnum propertyType;

    public PropertyTypeEntity() {
    }

    public PropertyTypeEntity(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public PropertyTypeEntity setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
        return this;
    }
}
