package bg.softuni.myrealestateproject.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity{

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity owner;

    @ManyToOne(optional = false)
    private OfferTypeEntity offerType;

    @ManyToOne(optional = false)
    private EstateTypeEntity estateType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int room;

    @Column(nullable = false)
    private int bed;

    @Column(nullable = false)
    private int bath;
    @ManyToOne(optional = false)
    private CityEntity city;

    @Column(nullable = false)
    private String area;

    @ManyToOne
    private PropertyTypeEntity propertyType;

    @Column(nullable = false)
    private float quadrature;

    @Column(nullable = false)
    private LocalDate activeFrom;

    @Column
    boolean isApproved;

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private List<ImageEntity> images = new ArrayList<>();

    public OfferEntity() {
    }

    public OfferTypeEntity getOfferType() {
        return offerType;
    }

    public OfferEntity setOfferType(OfferTypeEntity offerType) {
        this.offerType = offerType;
        return this;
    }

    public EstateTypeEntity getEstateType() {
        return estateType;
    }

    public OfferEntity setEstateType(EstateTypeEntity estateType) {
        this.estateType = estateType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public CityEntity getCity() {
        return city;
    }

    public OfferEntity setCity(CityEntity city) {
        this.city = city;
        return this;
    }

    public PropertyTypeEntity getPropertyType() {
        return propertyType;
    }

    public OfferEntity setPropertyType(PropertyTypeEntity propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public float getQuadrature() {
        return quadrature;
    }

    public OfferEntity setQuadrature(float quadrature) {
        this.quadrature = quadrature;
        return this;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public OfferEntity setApproved(boolean approved) {
        isApproved = approved;
        return this;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public OfferEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public OfferEntity setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
        return this;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public OfferEntity setImages(List<ImageEntity> images) {
        this.images = images;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public OfferEntity setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public OfferEntity setRoom(int room) {
        this.room = room;
        return this;
    }

    public int getBed() {
        return bed;
    }

    public OfferEntity setBed(int bed) {
        this.bed = bed;
        return this;
    }

    public int getBath() {
        return bath;
    }

    public OfferEntity setBath(int bath) {
        this.bath = bath;
        return this;
    }

    public String getArea() {
        return area;
    }

    public OfferEntity setArea(String area) {
        this.area = area;
        return this;
    }

}
