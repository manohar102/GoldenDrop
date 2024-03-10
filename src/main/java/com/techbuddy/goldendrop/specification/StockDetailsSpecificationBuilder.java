package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.StockDetail;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

public class StockDetailsSpecificationBuilder extends BaseSpecificationsBuilder {

    public StockDetailsSpecificationBuilder() {
        super(new ArrayList<>());
    }

    public Specification<StockDetail> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new StockDetailsSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new StockDetailsSpecification(params.get(i)));
        }

        return result;
    }
}
