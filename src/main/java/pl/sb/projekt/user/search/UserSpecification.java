package pl.sb.projekt.user.search;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.common.utils.SpecificationUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private final UserFilter userFilter;

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicateList = new ArrayList<>();

        SpecificationUtils.addLikePredicate(root.get(User.Fields.login), builder, predicateList, userFilter.getLogin());
        SpecificationUtils.addLikePredicate(root.get(User.Fields.firstName), builder, predicateList, userFilter.getFirstName());
        SpecificationUtils.addLikePredicate(root.get(User.Fields.lastName), builder, predicateList, userFilter.getLastName());
        SpecificationUtils.addBetweenPredicate(root.get(User.Fields.costPerHour), builder, predicateList, userFilter.getCostFrom(), userFilter.getCostTo());
        SpecificationUtils.addEqualPredicate(root.get(User.Fields.userRole), builder, predicateList, userFilter.getUserRole());

        return builder.and(predicateList.toArray(Predicate[]::new));
    }

}