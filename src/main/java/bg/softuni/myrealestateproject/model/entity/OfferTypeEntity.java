package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "offer_types")
public class OfferTypeEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferTypeEnum offerType;

    public OfferTypeEntity() {
    }

    public OfferTypeEntity(OfferTypeEnum offerType) {
        this.offerType = offerType;
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public OfferTypeEntity setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
        return this;
    }
}
