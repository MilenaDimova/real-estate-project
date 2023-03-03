package bg.softuni.myrealestateproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity{
    @Column
    private String name;

    @Column
    private int imageOrder;

    @ManyToOne
    private OfferEntity offer;

    public ImageEntity() {
    }

    public String getName() {
        return name;
    }

    public ImageEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public ImageEntity setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
        return this;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public ImageEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }
}
