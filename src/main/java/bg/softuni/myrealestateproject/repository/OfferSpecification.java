package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.binding.SearchOfferBindingModel;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OfferSpecification implements Specification<OfferEntity> {

    private final SearchOfferBindingModel searchOfferBindingModel;

    public OfferSpecification(SearchOfferBindingModel searchOfferBindingModel) {
        this.searchOfferBindingModel = searchOfferBindingModel;
    }

    @Override
    public Predicate toPredicate(Root<OfferEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        if (searchOfferBindingModel.getOfferType() != null && !searchOfferBindingModel.getOfferType().isEmpty()) {
            p.getExpressions().add(cb.and(cb.equal(root.join("offerType").get("offerType"), searchOfferBindingModel.getOfferType())));
        }

        if (searchOfferBindingModel.getCity() != null && !searchOfferBindingModel.getCity().isEmpty()) {
            p.getExpressions().add(cb.and(cb.equal(root.join("city").get("city"), searchOfferBindingModel.getCity())));
        }

        if (searchOfferBindingModel.getEstateType() != null && !searchOfferBindingModel.getEstateType().isEmpty()) {
            p.getExpressions().add(cb.and(cb.equal(root.join("estateType").get("estateType"), searchOfferBindingModel.getEstateType())));
        }

        if (searchOfferBindingModel.getPropertyType() != null && !searchOfferBindingModel.getPropertyType().isEmpty()) {
            p.getExpressions().add(cb.and(cb.equal(root.join("propertyType").get("propertyType"), searchOfferBindingModel.getPropertyType())));
        }

        if (searchOfferBindingModel.getMinPrice() != null) {
            p.getExpressions().add(cb.and(cb.greaterThanOrEqualTo(root.get("price"), searchOfferBindingModel.getMinPrice())));
        }

        if (searchOfferBindingModel.getMaxPrice() != null) {
            p.getExpressions().add(cb.and(cb.lessThanOrEqualTo(root.get("price"), searchOfferBindingModel.getMaxPrice())));
        }

        return p;
    }
}
