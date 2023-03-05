package bg.softuni.myrealestateproject.model.view;

import java.math.BigDecimal;

public class OfferViewModel {
    private Long id;
    private String offerType;
    private String estateType;
    private String propertyType;
    private int floor;
    private int bed;
    private int room;
    private int bath;
    private float quadrature;
    private String area;
    private String description;
    private BigDecimal price;

    public OfferViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOfferType() {
        return offerType;
    }

    public OfferViewModel setOfferType(String offerType) {
        this.offerType = offerType;
        return this;
    }

    public String getEstateType() {
        return estateType;
    }

    public OfferViewModel setEstateType(String estateType) {
        this.estateType = estateType;
        return this;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public OfferViewModel setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public OfferViewModel setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getBed() {
        return bed;
    }

    public OfferViewModel setBed(int bed) {
        this.bed = bed;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public OfferViewModel setRoom(int room) {
        this.room = room;
        return this;
    }

    public int getBath() {
        return bath;
    }

    public OfferViewModel setBath(int bath) {
        this.bath = bath;
        return this;
    }

    public float getQuadrature() {
        return quadrature;
    }

    public OfferViewModel setQuadrature(float quadrature) {
        this.quadrature = quadrature;
        return this;
    }

    public String getArea() {
        return area;
    }

    public OfferViewModel setArea(String area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
