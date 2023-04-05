package bg.softuni.myrealestateproject.repository;

import bg.softuni.myrealestateproject.model.binding.SearchOfferBindingModel;
import bg.softuni.myrealestateproject.model.entity.OfferEntity;
import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfferSpecification implements Specification<OfferEntity> {

    private final SearchOfferBindingModel searchOfferBindingModel;

    public OfferSpecification(SearchOfferBindingModel searchOfferBindingModel) {
        this.searchOfferBindingModel = searchOfferBindingModel;
    }

    @Override
    public Predicate toPredicate(Root<OfferEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<>();

        if (searchOfferBindingModel.getOfferType() != null && !searchOfferBindingModel.getOfferType().name().isEmpty()) {
            predicates.add(cb.and(cb.equal(root.join("offerType").get("offerType"), searchOfferBindingModel.getOfferType())));
        }

        if (searchOfferBindingModel.getCity() != null && !searchOfferBindingModel.getCity().name().isEmpty()) {
            predicates.add(cb.and(cb.equal(root.join("city").get("city"), searchOfferBindingModel.getCity())));
        }

        if (searchOfferBindingModel.getEstateType() != null && !searchOfferBindingModel.getEstateType().name().isEmpty()) {
            predicates.add(cb.and(cb.equal(root.join("estateType").get("estateType"), searchOfferBindingModel.getEstateType())));
        }

        if (searchOfferBindingModel.getPropertyType() != null && !searchOfferBindingModel.getPropertyType().name().isEmpty()) {
            predicates.add(cb.and(cb.equal(root.join("propertyType").get("propertyType"), searchOfferBindingModel.getPropertyType())));
        }

        if (searchOfferBindingModel.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), searchOfferBindingModel.getMinPrice()));
        }

        if (searchOfferBindingModel.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), searchOfferBindingModel.getMaxPrice()));
        }

        if (searchOfferBindingModel.getMinQuadrature() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("quadrature"), searchOfferBindingModel.getMinQuadrature()));
        }

        if (searchOfferBindingModel.getMaxQuadrature() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("quadrature"), searchOfferBindingModel.getMaxQuadrature()));
        }

        //This Shows only active offers which has activeFrom today or in the past
        predicates.add(cb.lessThanOrEqualTo(root.get("activeFrom"), LocalDate.now()));
        predicates.add(cb.equal(root.join("status").get("statusType"), StatusTypeEnum.ACTIVE));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
