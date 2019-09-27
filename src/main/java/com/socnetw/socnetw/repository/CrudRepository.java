package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.IdEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
@EnableTransactionManagement
public class CrudRepository<T extends IdEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    public T save(T var) {
        entityManager.persist(var);
        return var;
    }

    public void delete(Long id, Class<T> tClass) {
        entityManager.remove(findById(id, tClass));
    }

    public T update(T var) {
        findById(var.getId(), var.getClass());
        return entityManager.merge(var);
    }

    public T findById(Long id, Class<? extends IdEntity> tClass) {
        T var = (T) entityManager.find(tClass, id);
        Optional<T> optional = Optional.ofNullable(var);
        return optional.orElse(null);


    }

    public T findEntity(Class<?> tClass, T object) {
        T var = (T) entityManager.find(tClass, object);
        Optional<T> optional = Optional.ofNullable(var);
        return optional.orElse(null);
    }
}
