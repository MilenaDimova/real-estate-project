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
    private String url;

    @ManyToOne
    private OfferEntity offer;

    public ImageEntity() {
    }

    public ImageEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public ImageEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ImageEntity setUrl(String url) {
        this.url = url;
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
