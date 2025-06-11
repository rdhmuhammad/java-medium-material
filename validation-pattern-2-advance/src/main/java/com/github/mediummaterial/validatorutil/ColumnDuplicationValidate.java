package com.github.mediummaterial.validatorutil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ColumnDuplicationValidate<T, E> extends ValidationNode<T> {

    private final Class<E> entity;

    private final Class<T> requestClass;

    private final EntityManager entityManager;

    private final RequestClassLoader requestClassLoader;

    public ColumnDuplicationValidate(Class<E> entity, Class<T> requestClass, EntityManager entityManager, RequestClassLoader requestClassLoader) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.requestClassLoader = requestClassLoader;
        this.requestClass = requestClass;
    }

    @Override
    protected ValidationResult validate(T input) {
        List<String> classFields = requestClassLoader.findByClass(requestClass);
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(input);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        query.from(entity);


        List<Selection<Boolean>> subSelection = new ArrayList<>();
        for (String fieldName : classFields) {
            Subquery<Boolean> subquery = query.subquery(Boolean.class);
            Root<E> root = subquery.from(entity);
            Expression<Long> count = cb.count(root.get("id"));
            subquery.where(cb.equal(root.get(fieldName), beanWrapper.getPropertyValue(fieldName)));
            Expression<Boolean> isExist = cb.selectCase()
                    .when(cb.gt(count, 0), true)
                    .otherwise(false).as(Boolean.class);
            subquery.select(isExist);
            subSelection.add(subquery.alias("exist" + StringUtils.capitalize(fieldName)));
        }

        query.multiselect(subSelection.toArray(new Selection[0]));

        Tuple singleResult = entityManager.createQuery(query).setMaxResults(1).getSingleResult();
        try {
            for (String fieldName : classFields) {
                if (Boolean.TRUE.equals(singleResult.get("exist" + StringUtils.capitalize(fieldName)))) {
                    return ValidationResult.invalid(String.format("duplicated value %s on field %s", beanWrapper.getPropertyValue(fieldName), StringUtils.capitalize(fieldName)));
                }
            }
        } catch (Exception e) {
            log.error("ALL-COL-VALIDATION-ERROR -> {}", e.getMessage());
        }

        return ValidationResult.valid();
    }

}
