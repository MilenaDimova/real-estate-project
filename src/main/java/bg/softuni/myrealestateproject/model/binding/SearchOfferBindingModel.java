package bg.softuni.myrealestateproject.model.binding;

import bg.softuni.myrealestateproject.model.enums.CityNameEnum;
import bg.softuni.myrealestateproject.model.enums.EstateTypeEnum;
import bg.softuni.myrealestateproject.model.enums.OfferTypeEnum;
import bg.softuni.myrealestateproject.model.enums.PropertyTypeEnum;

public class SearchOfferBindingModel {

    private OfferTypeEnum offerType;

    private CityNameEnum city;

    private EstateTypeEnum estateType;

    private PropertyTypeEnum propertyType;

    private Integer minQuadrature;

    private Integer maxQuadrature;

    private Integer minPrice;

    private Integer maxPrice;

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public SearchOfferBindingModel setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
        return this;
    }


    public CityNameEnum getCity() {
        return city;
    }

    public SearchOfferBindingModel setCity(CityNameEnum city) {
        this.city = city;
        return this;
    }

    public EstateTypeEnum getEstateType() {
        return estateType;
    }

    public SearchOfferBindingModel setEstateType(EstateTypeEnum estateType) {
        this.estateType = estateType;
        return this;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public SearchOfferBindingModel setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
        return this;
    }

        public Integer getMinQuadrature() {
        return minQuadrature;
    }

    public SearchOfferBindingModel setMinQuadrature(Integer minQuadrature) {
        this.minQuadrature = minQuadrature;
        return this;
    }

    public Integer getMaxQuadrature() {
        return maxQuadrature;
    }

    public SearchOfferBindingModel setMaxQuadrature(Integer maxQuadrature) {
        this.maxQuadrature = maxQuadrature;
        return this;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public SearchOfferBindingModel setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public SearchOfferBindingModel setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public boolean isEmpty() {
        return ((offerType == null || offerType.name().isEmpty())
                && (city == null || city.name().isEmpty())
                && (estateType == null || estateType.name().isEmpty())
                && (propertyType == null || propertyType.name().isEmpty())
                && minPrice == null && maxPrice == null
                && minQuadrature == null || maxQuadrature == null);
    }
}
