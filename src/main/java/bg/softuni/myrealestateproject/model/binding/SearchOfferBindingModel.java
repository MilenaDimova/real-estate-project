package bg.softuni.myrealestateproject.model.binding;

public class SearchOfferBindingModel {

    private String  offerType;

    private String city;

    private String estateType;

    private String propertyType;


//    private Integer minQuadrature;
//
//    private Integer maxQuadrature;
//
    private Integer minPrice;

    private Integer maxPrice;

    public String getOfferType() {
        return offerType;
    }

    public SearchOfferBindingModel setOfferType(String offerType) {
        this.offerType = offerType;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SearchOfferBindingModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getEstateType() {
        return estateType;
    }

    public SearchOfferBindingModel setEstateType(String estateType) {
        this.estateType = estateType;
        return this;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public SearchOfferBindingModel setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

//    public Integer getMinQuadrature() {
//        return minQuadrature;
//    }
//
//    public SearchOfferBindingModel setMinQuadrature(Integer minQuadrature) {
//        this.minQuadrature = minQuadrature;
//        return this;
//    }
//
//    public Integer getMaxQuadrature() {
//        return maxQuadrature;
//    }
//
//    public SearchOfferBindingModel setMaxQuadrature(Integer maxQuadrature) {
//        this.maxQuadrature = maxQuadrature;
//        return this;
//    }
//
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
        boolean check =  ((offerType == null || offerType.isEmpty()) && (city == null || city.isEmpty())
                && (estateType == null || estateType.isEmpty()) && (propertyType == null || propertyType.isEmpty()) && minPrice == null && maxPrice == null);
        return check;
//        && minQuadrature == null || maxQuadrature == null && minPrice == null || maxPrice == null);
    }
}
