package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.ProductStockView;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

public class ProductStockViewSpecificationBuilder extends BaseSpecificationsBuilder {

    public ProductStockViewSpecificationBuilder() {
        super(new ArrayList<>());
    }

    public Specification<ProductStockView> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new ProductStockViewSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ProductStockViewSpecification(params.get(i)));
        }

        return result;
    }
}
