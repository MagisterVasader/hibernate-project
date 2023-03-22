package com.modsen.software.user_service.repository;

import com.modsen.software.user_service.domain.User;
import com.modsen.software.user_service.domain.UserDto;
import com.modsen.software.user_service.domain.UserPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUsers() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);

        TypedQuery<User> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public Page<User> getUsersPageable(UserPage page) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);

        TypedQuery<User> query = entityManager.createQuery(cq);
        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize());

        Long count = getUsersCount();

        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    public User getUserById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        cq.select(root);
        cq.where(predicate);

        TypedQuery<User> query = entityManager.createQuery(cq);

        return query.getSingleResult();
    }

    public String getUserFirstnameById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        cq.select(root.get("firstname"));
        cq.where(predicate);

        TypedQuery<String> query = entityManager.createQuery(cq);

        return query.getSingleResult();
    }

    public User getUserInfoById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        cq.multiselect(root.get("firstname"), root.get("lastname"));
        cq.where(predicate);

        TypedQuery<Object[]> query = entityManager.createQuery(cq);

        Object[] result = query.getSingleResult();
        return new User(null, (String) result[0], (String) result[1]);
    }

    public UserDto getUserDtoById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDto> cq = cb.createQuery(UserDto.class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        Path<String> firstnamePath = root.get("firstname");
        Path<String> lastnamePath = root.get("lastname");

        cq.select(cb.construct(UserDto.class, firstnamePath, lastnamePath));
        cq.where(predicate);

        TypedQuery<UserDto> query = entityManager.createQuery(cq);

        return query.getSingleResult();
    }

    public User getUserTupleById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<User> root = cq.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        Path<String> firstnamePath = root.get("firstname");
        Path<String> lastnamePath = root.get("lastname");

        cq.multiselect(firstnamePath, lastnamePath);
        cq.where(predicate);

        TypedQuery<Tuple> query = entityManager.createQuery(cq);

        Tuple tuple = query.getSingleResult();

        return new User(null, tuple.get(firstnamePath), tuple.get(lastnamePath));
    }

    @Transactional
    public User createUser(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Predicate namePredicate = cb.equal(root.get("firstname"), user.getFirstname());
        Predicate surnamePredicate = cb.equal(root.get("lastname"), user.getLastname());
        Predicate nameAndSurnamePredicate = cb.and(namePredicate, surnamePredicate);

        cq.select(root);
        cq.where(nameAndSurnamePredicate);

        TypedQuery<User> typedQuery = entityManager.createQuery(cq);

        if (typedQuery.getResultList().isEmpty()) {
            entityManager.persist(user);
            return user;
        } else {
            return null;
        }
    }

    @Transactional
    public User updateUser(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> cu = cb.createCriteriaUpdate(User.class);
        Root<User> root = cu.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), user.getId());

        cu.set(root.get("firstname"), user.getFirstname());
        cu.set(root.get("lastname"), user.getLastname());
        cu.where(predicate);

        Query query = entityManager.createQuery(cu);

        query.executeUpdate();
        return user;
    }

    @Transactional
    public void deleteUserById(Integer id) {
        if (Objects.isNull(id)) {
            return;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
        Root<User> root = cd.from(User.class);

        Predicate predicate = cb.equal(root.get("id"), id);

        cd.where(predicate);

        Query query = entityManager.createQuery(cd);

        query.executeUpdate();
    }

    private Long getUsersCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> root = cq.from(User.class);

        cq.select(cb.count(root));

        TypedQuery<Long> query = entityManager.createQuery(cq);

        return query.getSingleResult();
    }
}