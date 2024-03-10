package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.Product;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class ProductSpecification extends BaseSpecification<Product> {
    public ProductSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    protected Expression<String> getPath(SearchCriteria criteria, Root<Product> root) {
        if (criteria.getKey().equals("storeId")) {
            return root.get("store").get("id");
        }
        return root.get(criteria.getKey());
    }
}
