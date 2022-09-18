package pl.sb.clockify.project.search;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.sb.clockify.project.model.Project;
import pl.sb.clockify.common.utils.SpecificationUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProjectSpecification implements Specification<Project> {

    private final ProjectFilter projectFilter;

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        final List<Predicate> predicateList = new ArrayList<>();

        SpecificationUtils.addLikePredicate(root.get(Project.Fields.title), builder, predicateList, projectFilter.getTitle());
        SpecificationUtils.addOverBudgetPredicate(root.get(Project.Fields.budgetUse), builder, predicateList,
                projectFilter.isOverBudget());
        SpecificationUtils.addBetweenPredicate(root.get(Project.Fields.startDate), root.get(Project.Fields.endDate),
                builder, predicateList, projectFilter.getStartFrom(), projectFilter.getEndTo());
        SpecificationUtils.addUuidsListPredicate(root, predicateList, projectFilter.getUuids());

        return builder.and(predicateList.toArray(Predicate[]::new));
    }

}