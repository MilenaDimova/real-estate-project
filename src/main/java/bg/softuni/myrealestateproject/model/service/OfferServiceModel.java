package bg.softuni.myrealestateproject.model.service;

import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferServiceModel {
    private Long id;
    private CityNameEnum city;
    private String area;
    private OfferTypeEnum offerType;
    private EstateTypeEnum estateType;
    private PropertyTypeEnum propertyType;
    private int floor;
    private int room;
    private int bed;
    private int bath;
    private float quadrature;
    private BigDecimal price;
    private LocalDate activeFrom;
    private String description;
    private Long ownerId;

    public OfferServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public CityNameEnum getCity() {
        return city;
    }

    public OfferServiceModel setCity(CityNameEnum city) {
        this.city = city;
        return this;
    }

    public String getArea() {
        return area;
    }

    public OfferServiceModel setArea(String area) {
        this.area = area;
        return this;
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public OfferServiceModel setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
        return this;
    }

    public EstateTypeEnum getEstateType() {
        return estateType;
    }

    public OfferServiceModel setEstateType(EstateTypeEnum estateType) {
        this.estateType = estateType;
        return this;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public OfferServiceModel setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public OfferServiceModel setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public OfferServiceModel setRoom(int room) {
        this.room = room;
        return this;
    }

    public int getBed() {
        return bed;
    }

    public OfferServiceModel setBed(int bed) {
        this.bed = bed;
        return this;
    }

    public int getBath() {
        return bath;
    }

    public OfferServiceModel setBath(int bath) {
        this.bath = bath;
        return this;
    }

    public float getQuadrature() {
        return quadrature;
    }

    public OfferServiceModel setQuadrature(float quadrature) {
        this.quadrature = quadrature;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public OfferServiceModel setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public OfferServiceModel setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
