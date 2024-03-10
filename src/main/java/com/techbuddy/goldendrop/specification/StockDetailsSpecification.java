package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.StockDetail;
import jakarta.persistence.criteria.*;

public class StockDetailsSpecification extends BaseSpecification<StockDetail> {

    public StockDetailsSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    protected Expression<String> getPath(SearchCriteria criteria, Root<StockDetail> root) {
        if (criteria.getKey().equals("productId")) {
            return root.get("product").get("id");
        }
        return root.get(criteria.getKey());
    }
}
