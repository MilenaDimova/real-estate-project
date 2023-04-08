package bg.softuni.myrealestateproject.model.view;

import bg.softuni.myrealestateproject.model.enums.*;

import java.math.BigDecimal;
import java.util.List;

public class OfferViewModel {
    private Long id;
    private CityNameEnum city;
    private OfferTypeEnum offerType;
    private EstateTypeEnum estateType;
    private PropertyTypeEnum propertyType;
    private StatusTypeEnum statusType;
    private String activeFrom;
    private int floor;
    private int bed;
    private int room;
    private int bath;
    private float quadrature;
    private String area;
    private String description;
    private BigDecimal price;
    private OwnerViewModel owner;
    private List<Long> imagesIds;

    public OfferViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public CityNameEnum getCity() {
        return city;
    }

    public OfferViewModel setCity(CityNameEnum city) {
        this.city = city;
        return this;
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public OfferViewModel setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
        return this;
    }

    public EstateTypeEnum getEstateType() {
        return estateType;
    }

    public OfferViewModel setEstateType(EstateTypeEnum estateType) {
        this.estateType = estateType;
        return this;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public OfferViewModel setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public StatusTypeEnum getStatusType() {
        return statusType;
    }

    public OfferViewModel setStatusType(StatusTypeEnum statusType) {
        this.statusType = statusType;
        return this;
    }

    public String getActiveFrom() {
        return activeFrom;
    }

    public OfferViewModel setActiveFrom(String activeFrom) {
        this.activeFrom = activeFrom;
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

    public OwnerViewModel getOwner() {
        return owner;
    }

    public OfferViewModel setOwner(OwnerViewModel owner) {
        this.owner = owner;
        return this;
    }

    public List<Long> getImagesIds() {
        return imagesIds;
    }

    public OfferViewModel setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
        return this;
    }

    public String getHeadingImageUrl() {
        String url = "/api/images/download/";
        if (this.getImagesIds() != null && this.getImagesIds().size() > 0) {
            url += this.getImagesIds().get(0).toString();
        } else {
            url = "/images/no-image.png";
        }
        return url;
    }
}
