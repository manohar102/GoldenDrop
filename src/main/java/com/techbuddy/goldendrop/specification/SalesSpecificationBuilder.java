package com.techbuddy.goldendrop.specification;

import com.techbuddy.goldendrop.model.SaleRecord;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class SalesSpecificationBuilder extends BaseSpecificationsBuilder {

    public SalesSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }

    public Specification<SaleRecord> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new SalesSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new SalesSpecification(params.get(i)));
        }

        return result;
    }
}
