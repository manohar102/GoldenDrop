package com.techbuddy.goldendrop.specification;

import com.example.eshow.model.Tenant;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class TenantSpecificationBuilder extends BaseSpecificationsBuilder{
    public TenantSpecificationBuilder() {
        super(new ArrayList<>());
    }

    public Specification<Tenant> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new TenantSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new TenantSpecification(params.get(i)));
        }

        return result;
    }
}
