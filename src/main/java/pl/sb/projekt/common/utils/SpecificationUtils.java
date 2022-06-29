package pl.sb.projekt.common.utils;

import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.user.model.UserRole;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpecificationUtils {

    public static void addBetweenPredicate(final Path<BigDecimal> path, final CriteriaBuilder builder,
                                           final List<Predicate> predicateList, BigDecimal bottomValue, BigDecimal topValue) {
        if (Objects.nonNull(bottomValue) && Objects.nonNull(topValue)) {
            predicateList.add(builder.between(path, bottomValue, topValue));
        }
    }

    public static void addBetweenPredicate(final Path<LocalDate> from, final Path<LocalDate> to,
                                           final CriteriaBuilder builder, final List<Predicate> predicateList,
                                           final LocalDate startFrom, final LocalDate endTo) {

        if (Objects.nonNull(startFrom) && Objects.nonNull(endTo)) {
            predicateList.add(builder.greaterThanOrEqualTo(from, startFrom));
            predicateList.add(builder.lessThanOrEqualTo(to, endTo));
        } else if (Objects.nonNull(startFrom)) {
            predicateList.add(builder.greaterThanOrEqualTo(from, startFrom));
        } else if (Objects.nonNull(endTo)) {
            predicateList.add(builder.lessThanOrEqualTo(to, endTo));
        }
    }

    public static void addOverBudgetPredicate(final Path<BigDecimal> currentSpending, final Path<BigDecimal> budget, final CriteriaBuilder builder,
                                              final List<Predicate> predicateList, Boolean overBudget) {
        if (Objects.nonNull(overBudget)) {
            if (overBudget) {
                predicateList.add(builder.greaterThan(currentSpending, budget));
            } else {
                predicateList.add(builder.lessThan(currentSpending, budget));
            }
        }
    }

    public static void addEqualPredicate(final Path<String> path, final CriteriaBuilder builder,
                                         final List<Predicate> predicateList, UserRole userRole) {
        if (Objects.nonNull(userRole)) {
            predicateList.add(builder.equal(path, userRole));
        }
    }

    public static void addLikePredicate(final Path<String> path, final CriteriaBuilder builder,
                                        final List<Predicate> predicateList, final String value) {
        if (Objects.nonNull(value)) {
            predicateList.add(builder.like(builder.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

    public static void addUuidsListPredicate(final Root<Project> root,
                                             final List<Predicate> predicateList,
                                             final List<UUID> uuids) {
        if (Objects.nonNull(uuids)) {
            Predicate predicate = root.join("users").get("uuid").in(uuids);
            predicateList.add(predicate);
        }
    }

}