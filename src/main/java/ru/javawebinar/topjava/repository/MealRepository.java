package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll();

    Collection<Meal> getAllWithUserId(Integer userId);

    Collection<Meal> getFilteredByDate(List<Predicate<Meal>> datePredicates, Integer userId);
}