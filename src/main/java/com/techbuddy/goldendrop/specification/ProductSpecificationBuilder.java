package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.Product;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecificationBuilder extends BaseSpecificationsBuilder {

    public ProductSpecificationBuilder() {
        super(new ArrayList<>());
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new ProductSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ProductSpecification(params.get(i)));
        }

        return result;
    }
}
