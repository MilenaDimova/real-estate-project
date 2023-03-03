package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CityNameEnum city;

    public CityEntity() {
    }

    public CityEntity(CityNameEnum city) {
        this.city = city;
    }

    public CityNameEnum getCity() {
        return city;
    }

    public CityEntity setCity(CityNameEnum city) {
        this.city = city;
        return this;
    }
}
