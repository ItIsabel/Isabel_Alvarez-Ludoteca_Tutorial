package com.ccsw.tutorial.miam.cases.loan;

import com.ccsw.tutorial.miam.cases.loan.model.Loan;
import com.ccsw.tutorial.miam.cases.loan.model.LoanSearchDto;
import com.ccsw.tutorial.miam.common.criteria.SearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        }
        return null;
    }


    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

    public static Specification<Loan> dateBetween(LoanSearchDto searchDto) {
        return (root, query, cb) -> {
            return cb.and(
                    cb.lessThanOrEqualTo(root.get("startDate"), searchDto.getRequestDate()),
                    cb.greaterThanOrEqualTo(root.get("finishDate"), searchDto.getRequestDate())
            );
        };
    }
}
