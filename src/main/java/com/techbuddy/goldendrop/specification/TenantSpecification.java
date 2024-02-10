package com.techbuddy.goldendrop.specification;

import com.example.eshow.model.Tenant;
import lombok.extern.log4j.Log4j2;

import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Log4j2
public class TenantSpecification extends BaseSpecification<Tenant> {
    public TenantSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    protected Object getEnumValueIfEnum(String key, Object value, SearchOperation op) {
        switch (key) {
            case "createdDate":
                return parseDate(value, op);
            case "updatedDate":
                return parseDate(value, op);
            default:
                return super.getEnumValueIfEnum(key, value, op);
        }
    }

    @Override
    public Predicate toPredicate(Root<Tenant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate;
        if (this.criteria.getKey().equals("term")) {
            predicate = builder.or(
                    builder.like(root.get("organizationName"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("description"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("place"), "%" + criteria.getValue() + "%"),
                    builder.equal(root.get("category"), getEnumValueIfEnum("category", criteria.getValue(), SearchOperation.EQUALITY)));
        }else
            predicate = super.toPredicate(root, query, builder);
        return  predicate;
    }

    @Override
    protected Expression<String> getPath(SearchCriteria criteria, Root<Tenant> root) {
        return root.get(criteria.getKey());
    }

    private Object parseDate(Object value, SearchOperation op) {
        String valueStr = value.toString();
        if (op.equals(SearchOperation.LESS_THAN)) valueStr = valueStr + " 23:59:59";
        if (op.equals(SearchOperation.GREATER_THAN)) valueStr = valueStr + " 00:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(valueStr);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
