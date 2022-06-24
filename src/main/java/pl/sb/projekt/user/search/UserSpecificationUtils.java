package pl.sb.projekt.user.search;

import pl.sb.projekt.user.model.UserRole;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class UserSpecificationUtils {

    public static void addBetweenPredicate(final Path<BigDecimal> path, final CriteriaBuilder builder,
                                           final List<Predicate> predicateList, BigDecimal bottomValue, BigDecimal topValue) {
        if (Objects.nonNull(bottomValue) && Objects.nonNull(topValue)) {
            predicateList.add(builder.between(path, bottomValue, topValue));
        }
    }

    public static void addEqualPredicate(final Path<String> path, final CriteriaBuilder builder,
                                         final List<Predicate> predicateList, UserRole userRole) {
        if (Objects.nonNull(userRole)) {
            predicateList.add(builder.equal(path, userRole));
        }
    }

    static void addLikePredicate(final Path<String> path, final CriteriaBuilder builder,
                                 final List<Predicate> predicateList, final String value) {
        if (Objects.nonNull(value)) {
            predicateList.add(builder.like(builder.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

}