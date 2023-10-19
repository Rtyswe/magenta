package com.example.magenta.repo.impl;

import com.example.magenta.model.Distance;
import com.example.magenta.repo.CriteriaDistanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CriteriaDistanceRepositoryImpl implements CriteriaDistanceRepository {

    private final EntityManager entityManager;

    public List<Distance> getDistances(List<String> from, List<String> to) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distance> criteriaQuery = criteriaBuilder.createQuery(Distance.class);
        Root<Distance> root = criteriaQuery.from(Distance.class);
        criteriaQuery.select(root);

        Predicate[] predicates = new Predicate[from.size()];
        for (int i = 0; i < from.size(); ++i) {
            Predicate fromPredicate = criteriaBuilder.equal(root.get("from"), from.get(i));
            Predicate toPredicate = criteriaBuilder.equal(root.get("to"), to.get(i));
            predicates[i] = criteriaBuilder.and(fromPredicate, toPredicate);
        }
        Predicate finalPredicate = criteriaBuilder.or(predicates);
        criteriaQuery.where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
