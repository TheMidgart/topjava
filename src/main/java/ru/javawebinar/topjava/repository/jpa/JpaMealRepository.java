package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = entityManager.getReference(User.class,userId);
        meal.setUser(user);
        if (meal.isNew()) {
            entityManager.persist(meal);
        } else if (meal.getUser().getId() == userId) {
            entityManager.merge(meal);
        } else {
            return null;
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id",userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);
        if (meal!=null&&meal.getUser().getId()==userId){
        return meal;
        }
        else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) entityManager.createNamedQuery(Meal.ALL_SORTED)
                .setParameter("user_id",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createNamedQuery(Meal.GET_BETWEEN_HALF_AND_OPEN)
                .setParameter("user_id", userId)
                .setParameter("start_time",startDateTime)
                .setParameter("end_time",endDateTime)
                .getResultList();
    }
}