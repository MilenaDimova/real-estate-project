package bg.softuni.myrealestateproject.model.binding;

import bg.softuni.myrealestateproject.model.enums.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OfferAddBindingModel {
    private Long id;

    private StatusTypeEnum statusType;

    @NotNull(message = "City is required!")
    private CityNameEnum city;

    @NotBlank(message = "Area is required!")
    private String area;

    @NotNull(message = "Offer type is required!")
    private OfferTypeEnum offerType;

    @NotNull(message = "Estate type is required!")
    private EstateTypeEnum estateType;

    @NotNull(message = "Property type is required!")
    private PropertyTypeEnum propertyType;

    @NotNull
    @Min(value = 0, message = "The floor must be greater or equal than 0!")
    private int floor;

    @NotNull
    @Min(value = 0, message = "The rooms must be greater than 0!")
    private int room;

    @NotNull
    @Min(value = 0, message = "The beds must be greater than 0!")
    private int bed;

    @NotNull
    @Min(value = 0, message = "The baths must be greater than 0!")
    private int bath;

    @NotNull(message = "Quadrature is required!")
    private float quadrature;

    @NotNull
    @Positive(message = "Price must be positive number!")
    private BigDecimal price;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The offer cannot be created in the past!")
    private LocalDate activeFrom;

    private String description;

    private List<MultipartFile> uploadedImages;

    private boolean hasErrors;

    public OfferAddBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferAddBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public CityNameEnum getCity() {
        return city;
    }

    public OfferAddBindingModel setCity(CityNameEnum city) {
        this.city = city;
        return this;
    }

    public String getArea() {
        return area;
    }

    public OfferAddBindingModel setArea(String area) {
        this.area = area;
        return this;
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public OfferAddBindingModel setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
        return this;
    }

    public EstateTypeEnum getEstateType() {
        return estateType;
    }

    public OfferAddBindingModel setEstateType(EstateTypeEnum estateType) {
        this.estateType = estateType;
        return this;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public OfferAddBindingModel setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public OfferAddBindingModel setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getRoom() {
        return room;
    }

    public OfferAddBindingModel setRoom(int room) {
        this.room = room;
        return this;
    }

    public int getBed() {
        return bed;
    }

    public OfferAddBindingModel setBed(int bed) {
        this.bed = bed;
        return this;
    }

    public int getBath() {
        return bath;
    }

    public OfferAddBindingModel setBath(int bath) {
        this.bath = bath;
        return this;
    }

    public float getQuadrature() {
        return quadrature;
    }

    public OfferAddBindingModel setQuadrature(float quadrature) {
        this.quadrature = quadrature;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getActiveFrom() {
        if (this.activeFrom == null) {
            return LocalDate.now();
        }
        return activeFrom;
    }

    public OfferAddBindingModel setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<MultipartFile> getUploadedImages() {
        return uploadedImages;
    }

    public OfferAddBindingModel setUploadedImages(List<MultipartFile> images) {
        this.uploadedImages = images.stream().filter(i -> !i.isEmpty()).collect(Collectors.toList());
        return this;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public OfferAddBindingModel setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
        return this;
    }

    public StatusTypeEnum getStatusType() {
        return statusType;
    }

    public OfferAddBindingModel setStatusType(StatusTypeEnum statusType) {
        this.statusType = statusType;
        return this;
    }
}
