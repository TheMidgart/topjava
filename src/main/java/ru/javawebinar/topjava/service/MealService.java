package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {


    private final MealRepository repository;
    @Autowired
    public MealService (MealRepository repository ){
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection <Meal> getFilteredByDate(List<Predicate<Meal>> datePredicates, Integer userId){
        return checkNotFound(repository.getFilteredByDate(datePredicates,userId),"meals not found");
    }

    public Collection<Meal> getAllWithUserId(Integer userId){
        return checkNotFound(repository.getAllWithUserId(userId),"meals not found");
    }
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public Meal update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
        return meal;
    }

}