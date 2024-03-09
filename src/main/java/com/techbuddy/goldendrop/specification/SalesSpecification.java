package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.SaleRecord;
import jakarta.persistence.criteria.*;
import java.sql.Timestamp;

public class SalesSpecification extends BaseSpecification<SaleRecord> {

    public SalesSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<SaleRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getKey().equals("startTime") && criteria.getValue() != null) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("createdDate"),
                    Timestamp.valueOf(criteria.getValue().toString()));
        } else if (criteria.getKey().equals("endTime") && criteria.getValue() != null) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("createdDate"),
                    Timestamp.valueOf(criteria.getValue().toString()));
        }
        return super.toPredicate(root, query, criteriaBuilder);
    }

    @Override
    protected Expression<String> getPath(SearchCriteria criteria, Root<SaleRecord> root) {
        if (criteria.getKey().equals("storeId")) {
            return root.get("store").get("id");
        }
        return root.get(criteria.getKey());
    }
}
